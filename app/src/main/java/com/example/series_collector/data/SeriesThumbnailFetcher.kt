package com.example.series_collector.data

import com.example.series_collector.data.api.adpater.ApiResultError
import com.example.series_collector.data.api.adpater.ApiResultException
import com.example.series_collector.data.api.adpater.ApiResultSuccess
import com.example.series_collector.data.api.model.SeriesDto
import com.example.series_collector.data.room.entity.SeriesEntity
import com.example.series_collector.data.source.youtube.YoutubeDataSource
import kotlinx.coroutines.*
import javax.inject.Inject

class SeriesThumbnailFetcher @Inject constructor(
    private val youtubeDataSource: YoutubeDataSource
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
        val response = youtubeDataSource.getPlayLists(playListId = seriesId, limit = 1)
        return when (response) {
            is ApiResultSuccess -> response.data.items?.firstOrNull()?.snippet?.thumbnails?.medium?.url
            is ApiResultException, is ApiResultError -> null
        }
    }
}

