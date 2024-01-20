package com.example.series_collector.remote

import com.example.series_collector.data.remote.SeriesRemoteDataSource
import com.example.series_collector.remote.api.model.SeriesDTO
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.util.Calendar
import javax.inject.Inject

class SeriesRemoteDataSourceImpl @Inject constructor() : SeriesRemoteDataSource {

    private val firestore = Firebase.firestore

    override suspend fun getAllSeries(): List<SeriesDTO> =
        collection("Series")
            .get()
            .await().toObjects(SeriesDTO::class.java)

    override suspend fun getUpdatedSeries(lastUpdate: Calendar): List<SeriesDTO> {
        return collection("Series")
            .whereGreaterThanOrEqualTo("updatedAt", lastUpdate.time)
            .get()
            .await().toObjects(SeriesDTO::class.java)
    }


    private fun collection(path: String) = firestore.collection(path)
}

