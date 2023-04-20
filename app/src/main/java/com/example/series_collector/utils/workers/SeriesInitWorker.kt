package com.example.series_collector.utils.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.series_collector.data.Series
import com.example.series_collector.data.room.AppDatabase
import com.example.series_collector.data.room.SeriesDao
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.*

class SeriesInitWorker(
    context: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = coroutineScope{
        val firebaseService = Firebase.firestore
        try {
            firebaseService.collection("Series")
                .get()
                .addOnSuccessListener { result ->
                    val list = result.toObjects(Series::class.java)
                    Log.d("list",list.size.toString())
                    val database= AppDatabase.getInstance(applicationContext)

                    CoroutineScope(Dispatchers.IO).launch{
                        database.SeriesDao().insertAllSeries(list)
                    }

                    Result.success()
                }
                .addOnFailureListener { ex ->
                    // error message
                    Log.e(TAG, "Error database", ex)
                    Result.failure()
                }
        } catch (ex: Exception) {
            Log.e(TAG, "Error database", ex)
        }

        Result.success()
    }




    companion object {
        private const val TAG = "SeriesDatabaseWorker"
        const val WORK_TAG = "SeriesSyncWorker"
    }
}
