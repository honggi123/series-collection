package com.example.series_collector.data.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentId

data class Series(
    val seriesId: String,
    val name: String,
    val description: String,
    val channel: String,
    val genre: Int,
    val thumbnail: String
)