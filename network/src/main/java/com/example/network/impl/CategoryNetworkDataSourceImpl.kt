package com.example.network.impl

import com.example.data.source.network.CategoryNetworkDataSource
import com.example.model.category.Ad
import com.example.model.category.Category
import com.example.network.model.AdDto
import com.example.network.model.CategoryDTO
import com.example.network.model.toAd
import com.example.network.model.toCategory
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CategoryNetworkDataSourceImpl @Inject constructor() :
    CategoryNetworkDataSource {

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