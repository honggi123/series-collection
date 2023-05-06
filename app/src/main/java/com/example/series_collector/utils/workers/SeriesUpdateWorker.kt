package com.example.series_collector.utils.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.lifecycle.viewModelScope
import androidx.work.CoroutineWorker
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import androidx.work.await
import com.example.series_collector.data.Series
import com.example.series_collector.data.repository.SeriesRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*

@HiltWorker
class SeriesUpdateWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val seriesRepository: SeriesRepository
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = coroutineScope {

        seriesRepository.run {
            val forceInit = if (seriesRepository.isEmpty()) true else false
            updateSeries(forceInit)
        }

        Result.success()
    }


    companion object {
        private const val TAG = "SeriesUpdateWorker"
    }
}
