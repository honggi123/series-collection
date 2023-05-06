package com.example.series_collector.data

import com.example.series_collector.data.source.YoutubeDataSource
import kotlinx.coroutines.*
import javax.inject.Inject

class SeriesFetcher @Inject constructor(
    private val youtubeDataSource: YoutubeDataSource
) {

    suspend fun fetchSeriesThumbnail(list: List<Series>): List<Series> =
        withContext(Dispatchers.IO) {
            supervisorScope {
                val deffered = list.map { series ->
                    async {
                        if (series.thumbnail.isBlank()) {
                            val thumbnailUrl =
                                youtubeDataSource.getThumbnailImageUrl(series.seriesId)
                            series.copy(thumbnail = thumbnailUrl)
                        } else series
                    }
                }
                try {
                    deffered.awaitAll()
                } catch (e: Exception) {
                    e.printStackTrace()
                    list
                }
            }
        }


}

