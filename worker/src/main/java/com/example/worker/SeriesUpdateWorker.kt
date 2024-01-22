package com.example.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.data.source.local.SeriesLocalDataSource
import com.example.data.source.network.SeriesNetworkDataSource
import com.example.local.preference.PreferenceManager
import com.example.worker.util.SeriesThumbnailFetcher
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.supervisorScope
import java.util.Calendar

@HiltWorker
class SeriesUpdateWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val seriesLocalDataSource: SeriesLocalDataSource,
    private val seriesNetworkDataSource: SeriesNetworkDataSource,
    private val seriesThumbnailFetcher: SeriesThumbnailFetcher,
    private val preferenceManager: PreferenceManager
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = supervisorScope {
        try {
            seriesLocalDataSource.run {
                val forceInit = isEmpty()
                updateSeries(forceInit)
            }
        } catch (ex: Exception) {
            Result.failure()
        }

        Result.success()
    }

    private suspend fun updateSeries(forceInit: Boolean) {
        seriesNetworkDataSource.run {
            val lastUpdate = if (forceInit) null else preferenceManager.getLastUpdateDate()
            val seriesList = if (forceInit) getAllSeries() else getUpdatedSeries(lastUpdate!!)

            seriesThumbnailFetcher.invoke(seriesList)
                .forEach { seriesLocalDataSource.insertSeries(it) }
        }

        preferenceManager.putLastUpdateDate(Calendar.getInstance())
    }

}
