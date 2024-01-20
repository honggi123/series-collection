package com.example.series_collector.util

import com.example.series_collector.data.source.remote.api.adpater.ApiResultError
import com.example.series_collector.data.source.remote.api.adpater.ApiResultException
import com.example.series_collector.data.source.remote.api.adpater.ApiResultSuccess
import com.example.series_collector.data.source.remote.EpisodeRemoteDataSource
import com.example.series_collector.data.source.local.room.entity.SeriesEntity
import kotlinx.coroutines.*
import javax.inject.Inject

class SeriesThumbnailFetcher @Inject constructor(
    private val episodeRemoteDataSource: EpisodeRemoteDataSource
) {
    suspend operator fun invoke(list: List<SeriesEntity>): List<SeriesEntity> =
        withContext(Dispatchers.IO) {
            list.map { series ->
                async {
                    if (series.thumbnail.isNullOrBlank()) {
                        val url = getThumbnailUrl(series.id)
                        series.copy(thumbnail = url)
                    } else series
                }
            }.awaitAll()
        }

    private suspend fun getThumbnailUrl(seriesId: String): String? {
        val response = episodeRemoteDataSource.getEpisodeList(playListId = seriesId)
        return when (response) {
            is ApiResultSuccess -> response.data.items?.firstOrNull()?.snippet?.thumbnails?.medium?.url
            is ApiResultException, is ApiResultError -> null
        }
    }
}

