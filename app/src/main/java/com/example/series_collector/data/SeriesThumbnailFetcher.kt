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
            list.map { series ->
                async {
                    if (series.thumbnail.isNullOrBlank()) {
                        val url = getThumbnailUrl(series.seriesId)
                        series.copy(thumbnail = url)
                    } else series
                }
            }.awaitAll()
        }

    private suspend fun getThumbnailUrl(seriesId: String): String? {
        val playlistsResponse = youtubeDataSource.getPlayLists(playListId = seriesId, limit = 1).body()
        return playlistsResponse?.items?.firstOrNull()?.snippet?.thumbnails?.medium?.url
    }
}

