package com.example.series_collector.data.source

import com.example.series_collector.data.api.YoutubeService
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.series_collector.data.model.dto.SeriesVideo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class YoutubeDataSource @Inject constructor(
    private val youTubeService: YoutubeService
) {
    suspend fun getPlayLists(playListId: String, limit: Int) =
        youTubeService.getYoutubePlayListItems(id = playListId, maxResults = limit)

    fun getSearchResultStream(playlistId: String): Flow<PagingData<SeriesVideo>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 5),
            pagingSourceFactory = { PlaylistPagingSource(youTubeService, playlistId) }
        ).flow
    }

}