package com.example.series_collector.data.source.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.series_collector.remote.api.adpater.ApiResponse
import com.example.series_collector.remote.model.CategoryDTO
import com.example.series_collector.remote.model.EpisodeListsDto
import com.example.series_collector.remote.model.PageInfo
import com.example.series_collector.remote.model.SeriesEpisode
import com.example.series_collector.remote.paging.EpisodePagingSource
import kotlinx.coroutines.flow.Flow

interface EpisodeRemoteDataSource {

    // TODO change return type
    suspend fun getEpisodeList(playListId: String): ApiResponse<EpisodeListsDto>

    fun getEpisodeListStream(playlistId: String): Flow<PagingData<SeriesEpisode>>

}