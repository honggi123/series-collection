package com.example.series_collector.data

import androidx.room.Entity

@Entity(tableName = "Series")
data class Series(
    val name: String,
    val description: String,
    val have_count: Int,
    val genre: Int,
    val createdAt: Long
)
