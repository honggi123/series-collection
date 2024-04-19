package com.example.data.model

import com.example.model.episode.Episode
import com.example.model.episode.PageInfo
import com.example.network.model.NetworkEpisode
import com.example.network.model.NetworkPageInfo


fun NetworkPageInfo.toPageInfo(): PageInfo {
    return PageInfo(
        resultsPerPage = resultsPerPage,
        totalResults = totalResults,
    )
}

fun NetworkEpisode.toEpisode(): Episode {
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