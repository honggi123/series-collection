package com.example.series_collector.data.source

import com.example.series_collector.data.model.dto.CategoryDto
import com.example.series_collector.data.room.entity.SeriesEntity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject

class FirestoreDataSource @Inject constructor() {

    private val firestore = Firebase.firestore

    suspend fun getAllSeries(): List<SeriesEntity> =
        collection("Series")
            .get()
            .await().toObjects(SeriesEntity::class.java)

    suspend fun getUpdatedSeries(lastUpdate: Calendar): List<SeriesEntity> =
        collection("Series")
            .whereGreaterThanOrEqualTo("createdAt", lastUpdate.time)
            .get()
            .await().toObjects(SeriesEntity::class.java)


    suspend fun getCategorys(): List<CategoryDto> =
        collection("Category")
            .get()
            .await().toObjects(CategoryDto::class.java)

    private fun collection(path: String) = firestore.collection(path)

}