package com.example.series_collector.remote.impl

import com.example.series_collector.data.model.series.Series
import com.example.series_collector.data.source.remote.SeriesRemoteDataSource
import com.example.series_collector.remote.model.SeriesDTO
import com.example.series_collector.remote.model.toSeries
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.util.Calendar
import javax.inject.Inject

class SeriesRemoteDataSourceImpl @Inject constructor() : SeriesRemoteDataSource {

    private val firestore = Firebase.firestore

    override suspend fun getAllSeries(): List<Series> =
        collection("Series")
            .get()
            .await().toObjects(SeriesDTO::class.java)
            .map { it.toSeries() }

    override suspend fun getUpdatedSeries(lastUpdate: Calendar): List<Series> =
        collection("Series")
            .whereGreaterThanOrEqualTo("updatedAt", lastUpdate.time)
            .get()
            .await().toObjects(SeriesDTO::class.java)
            .map { it.toSeries() }

    private fun collection(path: String) = firestore.collection(path)
}

