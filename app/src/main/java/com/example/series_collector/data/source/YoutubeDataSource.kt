package com.example.series_collector.data.source

import com.example.series_collector.data.api.YouTubeService
import javax.inject.Inject

class YouTubeDataSource  @Inject constructor(
    private val youTubeService: YouTubeService
) {
    suspend fun getThumbNailImage(playListId: String): String =
        youTubeService.run {
            getYoutubePlayListItems(id = playListId, maxResults = 1)
                .items.get(0).snippet.thumbnails.default.url
        }
}