package com.example.series_collector.data

import com.example.series_collector.data.entitiy.Series
import com.example.series_collector.data.source.YoutubeDataSource
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SeriesThumbnailFetcher @Inject constructor(
    private val youtubeDataSource: YoutubeDataSource
) {
    suspend operator fun invoke(list: List<Series>): List<Series> =
        withContext(Dispatchers.IO) {
            list.map { series ->
                async {
                    if (series.thumbnail.isBlank()) {
                        series.copy(thumbnail = getThumbnailUrl(series.seriesId))
                    } else series
                }
            }.awaitAll()
        }

    private suspend fun getThumbnailUrl(seriesId: String): String =
        runCatching {
            youtubeDataSource.getPlayLists(playListId = seriesId, limit = 1)
                .items.get(0).snippet.thumbnails.medium.url
        }.getOrDefault("")


}

