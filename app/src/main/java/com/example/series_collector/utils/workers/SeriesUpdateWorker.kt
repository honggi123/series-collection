package com.example.series_collector.utils.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.lifecycle.viewModelScope
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.series_collector.data.Series
import com.example.series_collector.data.repository.SeriesRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.*

@HiltWorker
class SeriesUpdateWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val seriesRepository: SeriesRepository
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = coroutineScope {
        try {
            seriesRepository.run {
                val lastDate = getLastUpdateDate()
                val list = getUpdatedSeries(lastDate)
                insertAllSeries(list)
            }

        } catch (ex: Exception) {
            Log.e(TAG, "Error worker", ex)
        }

        Result.success()
    }


    companion object {
        private const val TAG = "SeriesUpdateWorker"
    }
}
