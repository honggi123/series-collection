package com.example.series_collector.remote.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.series_collector.data.source.remote.EpisodeRemoteDataSource
import com.example.series_collector.remote.api.adpater.ApiResponse
import com.example.series_collector.remote.api.adpater.ApiResultSuccess
import com.example.series_collector.remote.model.EpisodeListsDto
import com.example.series_collector.remote.model.SeriesEpisode
import com.example.series_collector.remote.api.service.YoutubeService
import com.example.series_collector.remote.model.PageInfo
import com.example.series_collector.remote.paging.EpisodePagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EpisodeRemoteDataSourceImpl @Inject constructor(
    private val youTubeService: YoutubeService
) : EpisodeRemoteDataSource {

    override suspend fun getEpisodeList(playListId: String): ApiResponse<EpisodeListsDto> {
        return youTubeService.getYoutubePlayListItems(id = playListId, maxResults = 1)
    }

    override fun getEpisodeListStream(playlistId: String): Flow<PagingData<SeriesEpisode>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 5),
            pagingSourceFactory = { EpisodePagingSource(youTubeService, playlistId) }
        ).flow
    }

}