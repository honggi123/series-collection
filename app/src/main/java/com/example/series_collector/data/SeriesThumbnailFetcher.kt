package com.example.series_collector.data

import com.example.series_collector.data.room.entity.SeriesEntity
import com.example.series_collector.data.source.YoutubeDataSource
import kotlinx.coroutines.*
import java.lang.NullPointerException
import javax.inject.Inject

class SeriesThumbnailFetcher @Inject constructor(
    private val youtubeDataSource: YoutubeDataSource
) {
    suspend operator fun invoke(list: List<SeriesEntity>): List<SeriesEntity> =
        withContext(Dispatchers.IO) {
            list.map { seriesEntity ->
                async {
                    if (seriesEntity.thumbnail.isNullOrBlank()) {
                        val url = getThumbnailUrl(seriesEntity.seriesId)
                        seriesEntity.copy(thumbnail = url)
                    } else seriesEntity
                }
            }.awaitAll()
        }

    private suspend fun getThumbnailUrl(seriesId: String): String =
        youtubeDataSource.getPlayLists(playListId = seriesId, limit = 1).body()
            ?.items?.get(0)?.snippet?.thumbnails?.medium?.url ?: ""


}

