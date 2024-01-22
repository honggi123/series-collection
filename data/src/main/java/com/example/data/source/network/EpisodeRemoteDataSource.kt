package com.example.data.source.network

import androidx.paging.PagingData
import com.example.model.common.Thumbnail
import com.example.model.episode.Episode
import com.example.model.episode.PageInfo
import kotlinx.coroutines.flow.Flow

interface EpisodeRemoteDataSource {

    fun getEpisodeListStream(seriesId: String): Flow<PagingData<Episode>>

    suspend fun getPageInfo(seriesId: String): PageInfo?

    suspend fun getThumbnailList(seriesId: String): List<Thumbnail>

}