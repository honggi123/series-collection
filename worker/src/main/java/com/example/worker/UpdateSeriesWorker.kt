package com.example.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.data.model.toSeries
import com.example.data.model.toSeriesEntity
import com.example.local.source.SeriesLocalDataSource
import com.example.network.source.SeriesNetworkDataSource
import com.example.worker.util.SeriesThumbnailFetcher
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.supervisorScope
import java.util.Calendar

@HiltWorker
class UpdateSeriesWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val seriesLocalDataSource: SeriesLocalDataSource,
    private val seriesNetworkDataSource: SeriesNetworkDataSource,
    private val seriesThumbnailFetcher: SeriesThumbnailFetcher,
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = supervisorScope {
        try {
            val lastUpdateDate = inputData.getLong(KEY_LAST_UPDATE_DATE, 0)
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = lastUpdateDate
            val updatedSeriesFromNetwork = seriesNetworkDataSource.getUpdatedSeries(calendar)
                .map { it.toSeries() }
            val seriesEntitiesWithThumbnails = seriesThumbnailFetcher.invoke(updatedSeriesFromNetwork)
                .map { it.toSeriesEntity() }

            seriesLocalDataSource.insertSeriesList(seriesEntitiesWithThumbnails)
            Result.success()
        } catch (ex: Exception) {
            Result.failure()
        }
    }

    companion object {
        const val KEY_LAST_UPDATE_DATE = "KEY_LAST_UPDATE_DATE"

        fun enqueue(workManager: WorkManager, updateDate: Calendar): OneTimeWorkRequest {
            val workRequest = OneTimeWorkRequestBuilder<UpdateSeriesWorker>()
                .setInputData(workDataOf(KEY_LAST_UPDATE_DATE to updateDate.timeInMillis))
                .build()
            workManager.enqueue(workRequest)
            return workRequest
        }
    }
}
