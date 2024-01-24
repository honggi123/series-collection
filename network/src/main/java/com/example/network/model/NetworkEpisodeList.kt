package com.example.network.model


data class NetworkEpisodeList(
    val etag: String,
    val items: List<NetworkEpisode>,
    val kind: String,
    val prevPageToken: String,
    val nextPageToken: String,
    val pageInfo: NetworkPageInfo?
)

data class NetworkEpisode(
    val id: String,
    val snippet: NetworkSnippet
)

data class NetworkPageInfo(
    val resultsPerPage: Int,
    val totalResults: Int?,
)

data class NetworkSnippet(
    val channelId: String,
    val channelTitle: String,
    val description: String,
    val playlistId: String,
    val position: Int,
    val publishedAt: String,
    val thumbnails: NetworkThumbnails?,
    val title: String,
    val videoOwnerChannelId: String,
    val videoOwnerChannelTitle: String
)

data class NetworkThumbnails(
    val high: NetworkThumbnailHigh,
    val medium: NetworkThumbnailMedium?,
    val standard: NetworkThumbnailStandard
)

data class NetworkThumbnailStandard(
    val height: Int,
    val url: String,
    val width: Int
)

data class NetworkThumbnailMedium(
    val height: Int,
    val url: String?,
    val width: Int
)

data class NetworkThumbnailHigh(
    val height: Int,
    val url: String,
    val width: Int
)

