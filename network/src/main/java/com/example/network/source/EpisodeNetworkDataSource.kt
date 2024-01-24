package com.example.network.source

import androidx.paging.PagingData
import com.example.network.model.NetworkEpisode
import com.example.network.model.NetworkPageInfo
import com.example.network.model.NetworkThumbnail
import kotlinx.coroutines.flow.Flow

interface EpisodeNetworkDataSource {

    fun getEpisodeListStream(seriesId: String): Flow<PagingData<NetworkEpisode>>

    suspend fun getPageInfo(seriesId: String): NetworkPageInfo?

    suspend fun getThumbnailList(seriesId: String): List<NetworkThumbnail>

}