package com.example.series_collector.remote.model

import com.example.series_collector.model.episode.Episode
import com.example.series_collector.model.episode.PageInfo

data class EpisodeListsDto(
    val etag: String,
    val items: List<EpisodeApiModel>,
    val kind: String,
    val prevPageToken: String,
    val nextPageToken: String,
    val pageInfo: PageInfoApiModel?
)

data class EpisodeApiModel(
    val id: String,
    val snippet: SnippetApiModel
)

data class PageInfoApiModel(
    val resultsPerPage: Int,
    val totalResults: Int?,
)

data class SnippetApiModel(
    val channelId: String,
    val channelTitle: String,
    val description: String,
    val playlistId: String,
    val position: Int,
    val publishedAt: String,
    val thumbnails: ThumbnailApiModel?,
    val title: String,
    val videoOwnerChannelId: String,
    val videoOwnerChannelTitle: String
)

data class ThumbnailApiModel(
    val high: ThumbnailHighApiModel,
    val medium: ThumbnailMediumApiModel?,
    val standard: ThumbnailStandardApiModel
)

data class ThumbnailStandardApiModel(
    val height: Int,
    val url: String,
    val width: Int
)

data class ThumbnailMediumApiModel(
    val height: Int,
    val url: String?,
    val width: Int
)

data class ThumbnailHighApiModel(
    val height: Int,
    val url: String,
    val width: Int
)

fun PageInfoApiModel.toPageInfo(): PageInfo {
    return PageInfo(
        resultsPerPage = resultsPerPage,
        totalResults = totalResults
    )
}

fun EpisodeApiModel.toEpisode(): Episode {
    return Episode(
        id = this.id,
        channelId = this.snippet.channelId,
        seriesId = this.snippet.playlistId,
        channelTitle = this.snippet.channelTitle,
        description = this.snippet.description,
        thumbnailUrl = this.snippet.thumbnails?.medium?.url ?: "",
        publishedAt = this.snippet.publishedAt,
        title = this.snippet.title,
    )
}