package com.example.worker.util

import com.example.model.series.Series
import com.example.network.source.EpisodeNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import javax.inject.Inject


class SeriesThumbnailFetcher @Inject constructor(
    private val episodeNetworkDataSource: EpisodeNetworkDataSource
) {
    suspend operator fun invoke(list: List<Series>): List<Series> =
        withContext(Dispatchers.IO) {
            supervisorScope {
                list.map { series ->
                    async {
                        if (series.thumbnailUrl.isNullOrBlank()) {
                            val url = getThumbnailUrl(series.id)
                            series.copy(thumbnailUrl = url)
                        } else series
                    }
                }.map {
                    try {
                        it.await()
                    } catch (e: Throwable) {
                        null
                    }
                }
            }.filterNotNull()
        }

    private suspend fun getThumbnailUrl(seriesId: String): String? {
        val list = episodeNetworkDataSource.getThumbnailList(seriesId = seriesId)
        return list.firstOrNull()?.url
    }
}
