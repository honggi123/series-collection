package com.example.series_collector.utils.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.series_collector.data.Series
import com.example.series_collector.data.room.SeriesDao
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SeriesDatabaseWorker @Inject constructor(
    private val networkService: FirebaseFirestore,
    private val seriesDao: SeriesDao,
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        networkService.collection("Series")
            .get()
            .addOnSuccessListener { result ->
                seriesDao.insertAllSeries(getResponseList(result))
            }
            .addOnFailureListener { ex ->
                // error message
                Log.e(TAG, "Error database", ex)
                Result.failure()
            }

        Result.success()
    }

    private fun getResponseList(result: QuerySnapshot): MutableList<Series> {
        return result.map {
            it.toObject(Series::class.java)
        }.toMutableList()
    }

    companion object {
        private const val TAG = "SeriesDatabaseWorker"
    }
}
