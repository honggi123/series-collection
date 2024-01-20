package com.example.series_collector.remote.impl

import com.example.series_collector.data.model.category.Ad
import com.example.series_collector.data.source.remote.CategoryRemoteDataSource
import com.example.series_collector.remote.model.AdDto
import com.example.series_collector.remote.model.CategoryDTO
import com.example.series_collector.remote.model.asDomain
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CategoryRemoteDataSourceImpl @Inject constructor() : CategoryRemoteDataSource {

    private val firestore = Firebase.firestore

    override suspend fun getCategorys(): List<CategoryDTO> =
        collection("Category")
            .orderBy("viewType", Query.Direction.DESCENDING)
            .get()
            .await().toObjects(CategoryDTO::class.java)

    override suspend fun getAdList(): List<Ad> =
        collection("Ad")
            .get()
            .await().toObjects(AdDto::class.java)
            .map { it.asDomain() }


    private fun collection(path: String) = firestore.collection(path)

}