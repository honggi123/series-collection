package com.example.network.source.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.network.api.adpater.ApiResultSuccess
import com.example.network.api.service.YoutubeService
import com.example.network.model.NetworkEpisode
import com.example.network.model.NetworkPageInfo
import com.example.network.model.NetworkThumbnail
import com.example.network.paging.EpisodePagingSource
import com.example.network.source.EpisodeNetworkDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EpisodeNetworkDataSourceImpl @Inject constructor(
    private val youTubeService: YoutubeService
) : EpisodeNetworkDataSource {

    override suspend fun getPageInfo(seriesId: String): NetworkPageInfo? {
        val result = youTubeService.getYoutubePlayListItems(id = seriesId, limit = 1)
        val pageInfo =
            if (result is ApiResultSuccess)
                result.data.pageInfo
            else null

        return pageInfo
    }

    override suspend fun getThumbnailList(seriesId: String): List<NetworkThumbnail> {
        val result = youTubeService.getYoutubePlayListItems(id = seriesId, limit = 1)
        val urls =
            if (result is ApiResultSuccess)
                result.data.items.map { it.snippet.thumbnails?.standard?.url }
            else null
        return urls?.map {
            NetworkThumbnail(it ?: "")
        } ?: emptyList()
    }

    override fun getEpisodeListStream(seriesId: String): Flow<PagingData<NetworkEpisode>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 5),
            pagingSourceFactory = { EpisodePagingSource(youTubeService, seriesId) }
        ).flow
    }

}