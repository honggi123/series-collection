package com.example.series_collector.model.episode

data class Episode(
    val id: String,
    val channelId: String,
    val seriesId: String,
    val channelTitle: String,
    val description: String,
    val thumbnailUrl: String,
    val publishedAt: String,
    val title: String,
)

