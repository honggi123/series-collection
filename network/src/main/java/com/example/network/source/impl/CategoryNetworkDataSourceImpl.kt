package com.example.network.source.impl

import com.example.network.model.NetworkAd
import com.example.network.model.NetworkCategory
import com.example.network.source.CategoryNetworkDataSource
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class CategoryNetworkDataSourceImpl @Inject constructor() :
    CategoryNetworkDataSource {

    private val firestore = Firebase.firestore

    override fun getCategorys(): Flow<List<NetworkCategory>> = callbackFlow {
        collection("Category")
            .orderBy("viewType", Query.Direction.DESCENDING)
            .get(Source.SERVER)
            .addOnSuccessListener {
                val response = it.toObjects(NetworkCategory::class.java)
                trySend(response)
            }.addOnFailureListener {
                close(it)
            }
        awaitClose()
    }

    override fun getAdList(): Flow<List<NetworkAd>> = callbackFlow {
        collection("Ad")
            .get(Source.SERVER)
            .addOnSuccessListener {
                val response = it.toObjects(NetworkAd::class.java)
                trySend(response)
            }.addOnFailureListener {
                throw it
            }
        awaitClose()
    }

    private fun collection(path: String) = firestore.collection(path)

}