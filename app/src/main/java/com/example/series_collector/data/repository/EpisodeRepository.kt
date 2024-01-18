package com.example.series_collector.data.repository

import androidx.paging.PagingData
import com.example.series_collector.data.SeriesThumbnailFetcher
import com.example.series_collector.data.api.model.SeriesVideo
import com.example.series_collector.data.room.FollowedSeriesDao
import com.example.series_collector.data.room.SeriesDao
import com.example.series_collector.data.source.firebase.FirestoreDataSource
import com.example.series_collector.data.source.preference.PreferenceDataStore
import com.example.series_collector.data.source.youtube.YoutubeDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class EpisodeRepository @Inject constructor(
    private val youtubeDataSource: YoutubeDataSource
) {
    fun getEpisodeList(seriesId: String): Flow<PagingData<SeriesVideo>> {
        return youtubeDataSource.getPlayListStream(seriesId)
    }
}