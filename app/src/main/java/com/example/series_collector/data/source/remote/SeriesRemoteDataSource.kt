package com.example.series_collector.data.source.remote

import com.example.series_collector.data.source.remote.api.model.SeriesDTO
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.util.Calendar
import javax.inject.Inject

class SeriesRemoteDataSource @Inject constructor() {

    private val firestore = Firebase.firestore

    suspend fun getAllSeries(): List<SeriesDTO> =
        collection("Series")
            .get()
            .await().toObjects(SeriesDTO::class.java)

    suspend fun getUpdatedSeries(lastUpdate: Calendar): List<SeriesDTO> =
        collection("Series")
            .whereGreaterThanOrEqualTo("updatedAt", lastUpdate.time)
            .get()
            .await().toObjects(SeriesDTO::class.java)


    private fun collection(path: String) = firestore.collection(path)
}

