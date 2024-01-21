package com.example.series_collector.util

import com.example.series_collector.model.series.Series
import com.example.series_collector.data.source.remote.EpisodeRemoteDataSource
import com.example.series_collector.remote.api.adpater.ApiResultError
import com.example.series_collector.remote.api.adpater.ApiResultException
import com.example.series_collector.remote.api.adpater.ApiResultSuccess
import com.example.series_collector.remote.impl.EpisodeRemoteDataSourceImpl
import com.example.series_collector.local.room.entity.SeriesEntity
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SeriesThumbnailFetcher @Inject constructor(
    private val episodeRemoteDataSource: EpisodeRemoteDataSource
) {
    suspend operator fun invoke(list: List<Series>): List<Series> =
        withContext(Dispatchers.IO) {
            list.map { series ->
                async {
                    if (series.thumbnailUrl.isNullOrBlank()) {
                        val url = getThumbnailUrl(series.id)
                        series.copy(thumbnailUrl = url)
                    } else series
                }
            }.awaitAll()
        }

    private suspend fun getThumbnailUrl(seriesId: String): String? {
        val list = episodeRemoteDataSource.getThumbnailList(seriesId = seriesId)
        return list.firstOrNull()?.url
    }
}

