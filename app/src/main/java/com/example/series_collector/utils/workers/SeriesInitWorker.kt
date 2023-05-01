package com.example.series_collector.utils.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.series_collector.data.Series
import com.example.series_collector.data.repository.SeriesRepository
import com.example.series_collector.data.room.AppDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.*

@HiltWorker
class SeriesInitWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val seriesRepository: SeriesRepository
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = coroutineScope{
        try {
            val list = seriesRepository.getAllSeries()
            seriesRepository.insertAllSeries(fetchSeriesThumbnail(list))
        } catch (ex: Exception) {
            Log.e(TAG, "Error worker", ex)
        }
        Result.success()
    }

    private suspend fun fetchSeriesThumbnail(list: List<Series>): List<Series> {
        val tempList = mutableListOf<Series>()
        withContext(Dispatchers.IO) {
            list.mapIndexed { index, series ->
                async {
                    series.apply {
                        val thumbnailUrl = seriesRepository.getThumbnailImageUrl(series.seriesId)
                        tempList.add(copy(thumbnail = thumbnailUrl))
                    }
                }
            }.awaitAll()
        }
        return tempList
    }
    companion object {
        private const val TAG = "SeriesInitWorker"
    }
}
