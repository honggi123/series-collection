package com.example.series_collector.data.source.remote

import androidx.paging.PagingData
import com.example.series_collector.model.episode.Episode
import com.example.series_collector.model.episode.PageInfo
import com.example.series_collector.model.episode.Thumbnail
import kotlinx.coroutines.flow.Flow

interface EpisodeRemoteDataSource {

    fun getEpisodeListStream(seriesId: String): Flow<PagingData<Episode>>

    suspend fun getPageInfo(seriesId: String): PageInfo?

    suspend fun getThumbnailList(seriesId: String): List<Thumbnail>

}