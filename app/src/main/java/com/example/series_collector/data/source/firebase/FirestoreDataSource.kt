package com.example.series_collector.data.source.firebase

import android.util.Log
import com.example.series_collector.data.api.model.AdDto
import com.example.series_collector.data.api.model.CategoryDTO
import com.example.series_collector.data.api.model.SeriesDTO
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject

class FirestoreDataSource @Inject constructor() {

    private val firestore = Firebase.firestore

    suspend fun getAllSeries(): List<SeriesDTO> =
        collection("Series")
            .get()
            .await().toObjects(SeriesDTO::class.java)

    suspend fun getUpdatedSeries(lastUpdate: Calendar): List<SeriesDTO> =
        collection("Series")
            .whereGreaterThanOrEqualTo("updatedAt", lastUpdate.time)
            .get()
            .await().toObjects(SeriesDTO::class.java)

    suspend fun getCategorys(): List<CategoryDTO> =
        collection("Category")
            .orderBy("viewType", Query.Direction.DESCENDING)
            .get()
            .await().toObjects(CategoryDTO::class.java)

    // TODO
    suspend fun getAds(): List<AdDto> =
        emptyList()

    private fun collection(path: String) = firestore.collection(path)

}