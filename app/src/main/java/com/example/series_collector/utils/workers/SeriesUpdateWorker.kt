package com.example.series_collector.utils.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.lifecycle.viewModelScope
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.series_collector.data.Series
import com.example.series_collector.data.repository.SeriesRepository
import com.example.series_collector.data.room.AppDatabase
import com.example.series_collector.data.room.SeriesDao
import com.example.series_collector.data.source.FirestoreDataSource
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltWorker
class SeriesUpdateWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val seriesRepository: SeriesRepository
    ) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = coroutineScope {
        val lastDate = seriesRepository.getLastUpdateDate()
        val list = seriesRepository.getUpdatedSeries(lastDate)
        insertSeries(list)
        Result.success()
    }

    private suspend fun insertSeries(taskAsync: List<Series>) =
        seriesRepository.insertAllSeries(taskAsync)


    companion object {
        private const val TAG = "SeriesDatabaseWorker"
    }
}
