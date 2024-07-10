package com.example.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.data.model.toSeries
import com.example.data.model.toSeriesEntity
import com.example.local.dao.SeriesDao
import com.example.network.source.SeriesNetworkDataSource
import com.example.worker.util.SeriesThumbnailFetcher
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import java.util.Calendar

@HiltWorker
class UpdateSeriesWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val seriesDao: SeriesDao,
    private val seriesNetworkDataSource: SeriesNetworkDataSource,
    private val seriesThumbnailFetcher: SeriesThumbnailFetcher,
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = coroutineScope {
        try {
            val lastUpdateDate = inputData.getLong(KEY_LAST_UPDATE_DATE, 0)
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = lastUpdateDate
            val updatedSeries = seriesNetworkDataSource.getUpdatedSeries(calendar)
                .map { it.map { it.toSeries() } }
            updatedSeries.map {
                seriesThumbnailFetcher.invoke(it)
                    .map { it.toSeriesEntity() }
            }.collectLatest {
                seriesDao.insertSeriesList(it)
            }

            Result.success()
        } catch (ex: Exception) {
            Result.failure()
        }
    }

    companion object {
        const val KEY_LAST_UPDATE_DATE = "KEY_LAST_UPDATE_DATE"

        fun enqueue(workManager: WorkManager, updateDate: Calendar): OneTimeWorkRequest {
            val workRequest = OneTimeWorkRequestBuilder<UpdateSeriesWorker>()
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(
                            NetworkType.CONNECTED
                        )
                        .build()
                )
                .setInputData(workDataOf(KEY_LAST_UPDATE_DATE to updateDate.timeInMillis))
                .build()
            workManager.enqueue(workRequest)
            return workRequest
        }
    }
}
