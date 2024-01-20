package com.example.series_collector.remote.model

data class EpisodeListsDto(
    val etag: String,
    val items: List<SeriesEpisode>,
    val kind: String,
    val prevPageToken: String,
    val nextPageToken: String,
    val pageInfo: PageInfo?
)

data class SeriesEpisode(
    val id: String,
    val snippet: Snippet
)

data class PageInfo(
    val resultsPerPage: Int,
    val totalResults: Int?,
)

data class Snippet(
    val channelId: String,
    val channelTitle: String,
    val description: String,
    val playlistId: String,
    val position: Int,
    val publishedAt: String,
    val resourceId: ResourceId,
    val thumbnails: Thumbnails?,
    val title: String,
    val videoOwnerChannelId: String,
    val videoOwnerChannelTitle: String
)

data class Thumbnails(
    val high: High,
    val maxres: Maxres,
    val medium: Medium?,
    val standard: Standard
)

data class Standard(
    val height: Int,
    val url: String,
    val width: Int
)

data class Maxres(
    val height: Int,
    val url: String,
    val width: Int
)

data class Medium(
    val height: Int,
    val url: String?,
    val width: Int
)


data class ResourceId(
    val kind: String,
    val videoId: String
)

data class High(
    val height: Int,
    val url: String,
    val width: Int
)
