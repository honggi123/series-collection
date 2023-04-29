package com.example.series_collector.data.source

import com.example.series_collector.data.Category
import com.example.series_collector.data.Series
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject

class FirestoreDataSource @Inject constructor() {
    suspend fun getUpdatedSeries(lastUpdate: Calendar): List<Series> {
        return lastUpdate.let {
            Firebase.firestore.collection("Series")
                .whereGreaterThanOrEqualTo("updateAt", it.time)
                .get()
                .await().toObjects(Series::class.java)
        }
    }

    suspend fun getCategorys(): MutableList<Category> = Firebase.firestore.collection("Category")
        .get()
        .await().toObjects(Category::class.java)


}