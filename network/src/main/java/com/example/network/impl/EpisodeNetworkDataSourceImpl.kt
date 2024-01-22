package com.example.network.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.source.network.EpisodeNetworkDataSource
import com.example.model.common.Thumbnail
import com.example.model.episode.Episode
import com.example.model.episode.PageInfo
import com.example.network.api.adpater.ApiResultSuccess
import com.example.network.api.service.YoutubeService
import com.example.network.model.toPageInfo
import com.example.network.paging.EpisodePagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EpisodeNetworkDataSourceImpl @Inject constructor(
    private val youTubeService: YoutubeService
) : EpisodeNetworkDataSource {

    override suspend fun getPageInfo(seriesId: String): PageInfo? {
        val result = youTubeService.getYoutubePlayListItems(id = seriesId, limit = 1)
        val pageInfoApiModel =
            if (result is ApiResultSuccess)
                result.data.pageInfo
            else null

        return pageInfoApiModel?.toPageInfo()
    }

    override suspend fun getThumbnailList(seriesId: String): List<Thumbnail> {
        val result = youTubeService.getYoutubePlayListItems(id = seriesId, limit = 1)
        val urls =
            if (result is ApiResultSuccess)
                result.data.items.map { it.snippet.thumbnails?.standard?.url }
            else null
        return urls?.map { Thumbnail(it ?: "") } ?: emptyList()
    }

    override fun getEpisodeListStream(seriesId: String): Flow<PagingData<Episode>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 5),
            pagingSourceFactory = { EpisodePagingSource(youTubeService, seriesId) }
        ).flow
    }

}