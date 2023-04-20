package com.example.series_collector.data.source

import com.example.series_collector.data.Series
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

class NetworkDataSource  @Inject constructor(){
     fun getUpdatedSeries(lastUpdate: Calendar?): Flow<MutableList<Series>>? {
        return lastUpdate?.let {
            Firebase.firestore.collection("Series")
                .whereGreaterThanOrEqualTo("updateAt", it.time)
                .snapshots()
                .map { querySnapshot ->
                    querySnapshot.toObjects(Series::class.java)
                }
        }
    }
}