package com.example.series_collector.data.source.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.series_collector.data.source.remote.api.adpater.ApiResponse
import com.example.series_collector.data.source.remote.api.model.EpisodeListsDto
import com.example.series_collector.data.source.remote.api.model.SeriesEpisode
import com.example.series_collector.data.source.remote.api.service.YoutubeService
import com.example.series_collector.data.source.remote.paging.EpisodePagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EpisodeRemoteDataSource @Inject constructor(
    private val youTubeService: YoutubeService
) {
    suspend fun getEpisodeList(playListId: String, limit: Int = 1) : ApiResponse<EpisodeListsDto> {
        return youTubeService.getYoutubePlayListItems(id = playListId, maxResults = limit)
    }

    fun getEpisodeListStream(playlistId: String): Flow<PagingData<SeriesEpisode>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 5),
            pagingSourceFactory = { EpisodePagingSource(youTubeService, playlistId) }
        ).flow
    }

}