package com.example.series_collector.data

import com.example.series_collector.data.entitiy.Series
import com.example.series_collector.data.source.YoutubeDataSource
import kotlinx.coroutines.*
import javax.inject.Inject

class SeriesThumbnailFetcher @Inject constructor(
    private val youtubeDataSource: YoutubeDataSource
) {
    suspend operator fun invoke(list: List<Series>): List<Series> =
        withContext(Dispatchers.IO) {
            list.map { series ->
                async {
                    try {
                        if (series.thumbnail.isBlank()) {
                            val thumbnailUrl =
                                youtubeDataSource.getPlayLists(playListId = series.seriesId, limit = 1)
                                    .items.get(0).snippet.thumbnails.medium.url
                            series.copy(thumbnail = thumbnailUrl)
                        } else series
                    } catch (e: Exception) {
                        e.printStackTrace()
                        series
                    }
                }
            }.awaitAll()
        }


}

