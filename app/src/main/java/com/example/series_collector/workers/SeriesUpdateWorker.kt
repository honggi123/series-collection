package com.example.series_collector.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.series_collector.data.source.remote.api.model.asEntity
import com.example.series_collector.data.repository.SeriesRepository
import com.example.series_collector.data.source.local.room.SeriesDao
import com.example.series_collector.data.source.local.preference.PreferenceDataStore
import com.example.series_collector.util.SeriesThumbnailFetcher
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.*
import java.util.Calendar

@HiltWorker
class SeriesUpdateWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val seriesDao: SeriesDao,
    private val repository: SeriesRepository,
    private val seriesThumbnailFetcher: SeriesThumbnailFetcher,
    private val preferenceDataStore: PreferenceDataStore
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = supervisorScope {
        try {
            repository.run {
                val forceInit = if (isEmpty()) true else false
                updateSeries(forceInit)
            }
        } catch (ex: Exception) {
            Result.failure()
        }

        Result.success()
    }

    suspend private fun updateSeries(forceInit: Boolean) {
        repository.run {
            val lastUpdate = if (forceInit) null else preferenceDataStore.getLastUpdateDate()
            val seriesList = if (forceInit) getRemoteAllSeries() else getRemoteUpdatedSeries(lastUpdate)
            val entityList = seriesList.map { it?.asEntity() }

            seriesThumbnailFetcher.invoke(entityList)
                .forEach { seriesDao.insertSeries(it) }
        }

        preferenceDataStore.putLastUpdateDate(Calendar.getInstance())
    }
}
