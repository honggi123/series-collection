package com.example.worker.util

import com.example.model.series.Series
import com.example.network.source.EpisodeNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import javax.inject.Inject


class SeriesThumbnailFetcher @Inject constructor(
    private val episodeNetworkDataSource: EpisodeNetworkDataSource
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
        val list = episodeNetworkDataSource.getThumbnailList(seriesId = seriesId)
        return list.firstOrNull()?.url
    }
}

