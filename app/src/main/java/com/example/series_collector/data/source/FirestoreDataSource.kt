package com.example.series_collector.data.source

import com.example.series_collector.data.model.Category
import com.example.series_collector.data.room.entity.Series
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject

class FirestoreDataSource @Inject constructor() {

    private val firestore = Firebase.firestore

    suspend fun getAllSeries(): List<Series> =
        collection("Series")
            .get()
            .await().toObjects(Series::class.java)

    suspend fun getUpdatedSeries(lastUpdate: Calendar): List<Series> =
        collection("Series")
            .whereGreaterThanOrEqualTo("createdAt", lastUpdate.time)
            .get()
            .await().toObjects(Series::class.java)


    suspend fun getCategorys(): List<Category> =
        collection("Category")
            .get()
            .await().toObjects(Category::class.java)

    private fun collection(path: String) = firestore.collection(path)

}