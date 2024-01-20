package com.example.series_collector.data.source.remote

import com.example.series_collector.data.source.remote.api.model.AdDto
import com.example.series_collector.data.source.remote.api.model.CategoryDTO
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CategoryRemoteDataSource @Inject constructor() {

    private val firestore = Firebase.firestore

    suspend fun getCategorys(): List<CategoryDTO> =
        collection("Category")
            .orderBy("viewType", Query.Direction.DESCENDING)
            .get()
            .await().toObjects(CategoryDTO::class.java)

    suspend fun getAds(): List<AdDto> =
        collection("Ad")
            .get()
            .await().toObjects(AdDto::class.java)

    private fun collection(path: String) = firestore.collection(path)

}