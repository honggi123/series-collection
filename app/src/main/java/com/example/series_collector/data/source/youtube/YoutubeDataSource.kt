package com.example.series_collector.data.source.youtube

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.series_collector.data.api.ApiResponse
import com.example.series_collector.data.api.YoutubeService
import com.example.series_collector.data.model.dto.PlayListsDto
import com.example.series_collector.data.model.dto.SeriesVideo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class YoutubeDataSource @Inject constructor(
    private val youTubeService: YoutubeService
) {
    suspend fun getPlayLists(playListId: String, limit: Int) : ApiResponse<PlayListsDto> {
       return youTubeService.getYoutubePlayListItems(id = playListId, maxResults = limit)
    }

    fun getSearchResultStream(playlistId: String): Flow<PagingData<SeriesVideo>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 5),
            pagingSourceFactory = { PlaylistPagingSource(youTubeService, playlistId) }
        ).flow
    }

}