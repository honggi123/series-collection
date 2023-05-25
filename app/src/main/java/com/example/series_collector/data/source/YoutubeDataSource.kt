package com.example.series_collector.data.source

import com.example.series_collector.data.api.YoutubeService
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.series_collector.data.api.PageInfo
import com.example.series_collector.data.api.SeriesVideo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class YoutubeDataSource @Inject constructor(
    private val youTubeService: YoutubeService
) {
    suspend fun getThumbnailImageUrl(playListId: String): String =
        youTubeService.getYoutubePlayListItems(id = playListId, maxResults = 1)
            .items.get(0).snippet.thumbnails.medium.url



    fun getSearchResultStream(playlistId: String): Flow<PagingData<SeriesVideo>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 5),
            pagingSourceFactory = { PlaylistPagingSource(youTubeService, playlistId) }
        ).flow
    }

    suspend fun getPageInfo(playListId: String): PageInfo =
        youTubeService.run {
            getYoutubePlayListItems(id = playListId, maxResults = 1)
                .pageInfo
        }


}