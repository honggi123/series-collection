package com.example.series_collector.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.series_collector.data.local.SeriesLocalDataSource
import com.example.series_collector.data.remote.SeriesRemoteDataSource
import com.example.series_collector.remote.api.model.asEntity
import com.example.series_collector.local.preference.PreferenceManager
import com.example.series_collector.util.SeriesThumbnailFetcher
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.*
import java.util.Calendar

@HiltWorker
class SeriesUpdateWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val seriesLocalDataSource: SeriesLocalDataSource,
    private val seriesRemoteDataSource: SeriesRemoteDataSource,
    private val seriesThumbnailFetcher: SeriesThumbnailFetcher,
    private val preferenceManager: PreferenceManager
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = supervisorScope {
        try {
            seriesLocalDataSource.run {
                val forceInit = if (isEmpty()) true else false
                updateSeries(forceInit)
            }
        } catch (ex: Exception) {
            Result.failure()
        }

        Result.success()
    }

    suspend private fun updateSeries(forceInit: Boolean) {
        seriesRemoteDataSource.run {
            val lastUpdate = if (forceInit) null else preferenceManager.getLastUpdateDate()
            val seriesList = if (forceInit) getAllSeries() else getUpdatedSeries(lastUpdate!!)
            val entityList = seriesList.map { it?.asEntity() }

            seriesThumbnailFetcher.invoke(entityList)
                .forEach { seriesLocalDataSource.insertSeries(it) }
        }

        preferenceManager.putLastUpdateDate(Calendar.getInstance())
    }
}
