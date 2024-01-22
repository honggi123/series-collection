package com.example.worker.util

import com.example.data.source.network.EpisodeRemoteDataSource
import com.example.model.series.Series
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
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

