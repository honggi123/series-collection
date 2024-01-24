package com.example.network.source.impl

import com.example.network.model.NetworkSeries
import com.example.network.source.SeriesNetworkDataSource
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.util.Calendar
import javax.inject.Inject

class SeriesNetworkDataSourceImpl @Inject constructor() :
    SeriesNetworkDataSource {

    private val firestore = Firebase.firestore

    override suspend fun getAllSeries(): List<NetworkSeries> =
        collection("Series")
            .get()
            .await().toObjects(NetworkSeries::class.java)

    override suspend fun getUpdatedSeries(lastUpdate: Calendar): List<NetworkSeries> =
        collection("Series")
            .whereGreaterThanOrEqualTo("updatedAt", lastUpdate.time)
            .get()
            .await().toObjects(NetworkSeries::class.java)

    private fun collection(path: String) = firestore.collection(path)
}

