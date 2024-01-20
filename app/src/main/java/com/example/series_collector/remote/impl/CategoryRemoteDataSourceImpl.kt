package com.example.series_collector.remote.impl

import com.example.series_collector.data.model.category.Ad
import com.example.series_collector.data.model.category.Category
import com.example.series_collector.data.source.remote.CategoryRemoteDataSource
import com.example.series_collector.remote.model.AdDto
import com.example.series_collector.remote.model.CategoryDTO
import com.example.series_collector.remote.model.toAd
import com.example.series_collector.remote.model.toCategory
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CategoryRemoteDataSourceImpl @Inject constructor() : CategoryRemoteDataSource {

    private val firestore = Firebase.firestore

    override suspend fun getCategorys(): List<Category> =
        collection("Category")
            .orderBy("viewType", Query.Direction.DESCENDING)
            .get()
            .await().toObjects(CategoryDTO::class.java)
            .map { it.toCategory() }

    override suspend fun getAdList(): List<Ad> =
        collection("Ad")
            .get()
            .await().toObjects(AdDto::class.java)
            .map { it.toAd() }

    private fun collection(path: String) = firestore.collection(path)

}