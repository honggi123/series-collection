package com.example.series_collector.data.model

data class Series(
    val seriesId: String,
    val name: String,
    val description: String,
    val channel: String,
    val genre: Int,
    val thumbnail: String
)