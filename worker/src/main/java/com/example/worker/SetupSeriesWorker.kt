package com.example.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.data.model.toSeries
import com.example.data.model.toSeriesEntity
import com.example.local.dao.SeriesDao
import com.example.network.source.SeriesNetworkDataSource
import com.example.worker.util.SeriesThumbnailFetcher
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.coroutineScope

@HiltWorker
class SetupSeriesWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val seriesDao: SeriesDao,
    private val seriesNetworkDataSource: SeriesNetworkDataSource,
    private val seriesThumbnailFetcher: SeriesThumbnailFetcher,
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = coroutineScope {
        try {
            val allSeries = seriesNetworkDataSource.getAllSeries()
                .map { it.toSeries() }
            val seriesEntitiesWithThumbnails = seriesThumbnailFetcher(allSeries)
                .map { it.toSeriesEntity() }

            seriesDao.insertSeriesList(seriesEntitiesWithThumbnails)
            Result.success()
        } catch (ex: Exception) {
            Result.failure()
        }
    }

    companion object {

        const val KEY_UNIQUE_NAME = "KEY_SETUP_WORK"

        fun enqueue(workManager: WorkManager): OneTimeWorkRequest {
            val workRequest = OneTimeWorkRequestBuilder<SetupSeriesWorker>()
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(
                            NetworkType.CONNECTED
                        )
                        .build()
                )
                .build()
             workManager
                .beginUniqueWork(KEY_UNIQUE_NAME, ExistingWorkPolicy.APPEND_OR_REPLACE, workRequest)
                .enqueue()
            return workRequest
        }
    }
}