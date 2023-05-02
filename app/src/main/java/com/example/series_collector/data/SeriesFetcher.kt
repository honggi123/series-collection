package com.example.series_collector.data

import com.example.series_collector.data.source.YoutubeDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SeriesFetcher @Inject constructor(
    private val youtubeDataSource: YoutubeDataSource
) {
    suspend fun fetchSeriesThumbnail(list: List<Series>): List<Series> {
        val tempList = mutableListOf<Series>()
        withContext(Dispatchers.IO) {
            list.map { series ->
                async {
                    if (series.thumbnail.isNullOrEmpty()) {
                        series.apply {
                            val thumbnailUrl = youtubeDataSource.getThumbnailImageUrl(series.seriesId)
                            tempList.add(copy(thumbnail = thumbnailUrl))
                        }
                    } else {
                        tempList.add(series)
                    }
                }
            }.awaitAll()
        }
        return tempList
    }
}

