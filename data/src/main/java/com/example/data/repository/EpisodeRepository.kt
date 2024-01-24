package com.example.data.repository

import androidx.paging.PagingData
import com.example.model.episode.Episode
import com.example.model.episode.PageInfo
import kotlinx.coroutines.flow.Flow

interface EpisodeRepository {

    fun getEpisodeListStream(seriesId: String)  : Flow<PagingData<Episode>>

    suspend fun getPageInfo(seriesId: String)  : PageInfo?
}