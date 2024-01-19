package com.example.series_collector.data.repository

import androidx.paging.PagingData
import com.example.series_collector.data.api.model.SeriesEpisode
import com.example.series_collector.data.source.youtube.YoutubeDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class EpisodeRepository @Inject constructor(
    private val youtubeDataSource: YoutubeDataSource
) {
    fun getEpisodeList(seriesId: String): Flow<PagingData<SeriesEpisode>> {
        return youtubeDataSource.getPlayListStream(seriesId)
    }
}