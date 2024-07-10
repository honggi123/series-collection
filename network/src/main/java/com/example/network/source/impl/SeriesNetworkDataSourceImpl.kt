package com.example.network.source.impl

import com.example.network.model.NetworkSeries
import com.example.network.source.SeriesNetworkDataSource
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.Calendar
import javax.inject.Inject

class SeriesNetworkDataSourceImpl @Inject constructor() :
    SeriesNetworkDataSource {

    private val firestore = Firebase.firestore

    override fun getAllSeries(): Flow<List<NetworkSeries>> = callbackFlow {
        collection("Series")
            .get(Source.SERVER)
            .addOnSuccessListener {
                val response = it.toObjects(NetworkSeries::class.java)
                trySend(response)
            }.addOnFailureListener {
                throw it
            }
        awaitClose()
    }

    override fun getUpdatedSeries(lastUpdate: Calendar): Flow<List<NetworkSeries>> = callbackFlow {
        collection("Series")
            .whereGreaterThanOrEqualTo("updatedAt", lastUpdate.time)
            .get(Source.SERVER)
            .addOnSuccessListener {
                val response = it.toObjects(NetworkSeries::class.java)
                trySend(response)
            }.addOnFailureListener {
                throw it
            }
        awaitClose()
    }

    private fun collection(path: String) = firestore.collection(path)
}

