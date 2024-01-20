package com.example.series_collector.remote.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.series_collector.data.model.episode.Episode
import com.example.series_collector.data.model.episode.PageInfo
import com.example.series_collector.data.model.episode.Thumbnail
import com.example.series_collector.data.source.remote.EpisodeRemoteDataSource
import com.example.series_collector.remote.api.adpater.ApiResultSuccess
import com.example.series_collector.remote.api.service.YoutubeService
import com.example.series_collector.remote.model.toPageInfo
import com.example.series_collector.remote.paging.EpisodePagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EpisodeRemoteDataSourceImpl @Inject constructor(
    private val youTubeService: YoutubeService
) : EpisodeRemoteDataSource {

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