package com.example.network.source.impl

import com.example.network.model.NetworkAd
import com.example.network.model.NetworkCategory
import com.example.network.source.CategoryNetworkDataSource
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CategoryNetworkDataSourceImpl @Inject constructor() :
    CategoryNetworkDataSource {

    private val firestore = Firebase.firestore

    override suspend fun getCategorys() =
        collection("Category")
            .orderBy("viewType", Query.Direction.DESCENDING)
            .get()
            .await().toObjects(NetworkCategory::class.java)

    override suspend fun getAdList() =
        collection("Ad")
            .get()
            .await().toObjects(NetworkAd::class.java)

    private fun collection(path: String) = firestore.collection(path)

}