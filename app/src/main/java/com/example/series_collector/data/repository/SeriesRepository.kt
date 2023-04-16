package com.example.series_collector.data.repository

import com.example.series_collector.data.Series
import com.example.series_collector.data.room.SeriesDao
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SeriesRepository @Inject constructor(
    private val networkService: FirebaseFirestore,
    private val seriesDao: SeriesDao
) {

    fun updateSeries() {
    }
}