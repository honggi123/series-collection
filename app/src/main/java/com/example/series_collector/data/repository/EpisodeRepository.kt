package com.example.series_collector.data.repository

import androidx.paging.PagingData
import com.example.series_collector.model.episode.Episode
import com.example.series_collector.model.episode.PageInfo
import kotlinx.coroutines.flow.Flow

interface EpisodeRepository {

    fun getEpisodeListStream(seriesId: String)  : Flow<PagingData<Episode>>

    suspend fun getPageInfo(seriesId: String)  : PageInfo?
}