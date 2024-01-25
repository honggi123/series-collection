package com.example.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.data.model.toSeries
import com.example.data.model.toSeriesEntity
import com.example.local.source.SeriesLocalDataSource
import com.example.network.source.SeriesNetworkDataSource
import com.example.worker.util.SeriesThumbnailFetcher
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.supervisorScope

@HiltWorker
class SetupSeriesWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val seriesLocalDataSource: SeriesLocalDataSource,
    private val seriesNetworkDataSource: SeriesNetworkDataSource,
    private val seriesThumbnailFetcher: SeriesThumbnailFetcher,
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = supervisorScope {
        try {
            val allSeries = seriesNetworkDataSource.getAllSeries()
                .map { it.toSeries() }
            val seriesEntitiesWithThumbnails = seriesThumbnailFetcher(allSeries)
                .map { it.toSeriesEntity() }

            seriesLocalDataSource.insertSeriesList(seriesEntitiesWithThumbnails)
            Result.success()
        } catch (ex: Exception) {
            Result.failure()
        }
    }

    companion object {

        const val KEY_UNIQUE_NAME = "KEY_SETUP_WORK"

        fun enqueue(workManager: WorkManager): OneTimeWorkRequest {
            val workRequest = OneTimeWorkRequestBuilder<SetupSeriesWorker>()
                .build()
             workManager
                .beginUniqueWork(KEY_UNIQUE_NAME, ExistingWorkPolicy.APPEND_OR_REPLACE, workRequest)
                .enqueue()
            return workRequest
        }
    }
}