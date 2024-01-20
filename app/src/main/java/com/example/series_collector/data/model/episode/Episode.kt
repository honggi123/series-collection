package com.example.series_collector.data.model.episode

data class Episode(
    val info: EpisodeInfo
)

data class EpisodeInfo(
    val channelId: String,
    val channelTitle: String,
    val description: String,
    val playlistId: String,
    val position: Int,
    val publishedAt: String,
    val thumbnails: Thumbnails?,
    val title: String,
    val videoOwnerChannelId: String,
    val videoOwnerChannelTitle: String
)

data class Thumbnails(
    val high: High,
    val medium: Medium?,
    val standard: Standard
)

data class Standard(
    val height: Int,
    val url: String,
    val width: Int
)

data class Medium(
    val height: Int,
    val url: String?,
    val width: Int
)

data class High(
    val height: Int,
    val url: String,
    val width: Int
)

