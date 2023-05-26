package com.example.series_collector.data.repository

import com.example.series_collector.data.entitiy.Series
import com.example.series_collector.data.SeriesThumbnailFetcher
import com.example.series_collector.data.room.SeriesDao
import com.example.series_collector.data.source.FirestoreDataSource
import com.example.series_collector.data.source.YoutubeDataSource
import kotlinx.coroutines.*
import java.util.*
import javax.inject.Inject

class SeriesRepository @Inject constructor(
    private val seriesDao: SeriesDao,
    private val firestoreDataSource: FirestoreDataSource,
    private val youtubeDataSource: YoutubeDataSource,
    private val seriesThumbnailFetcher: SeriesThumbnailFetcher
) {

    suspend fun isEmpty() = seriesDao.isEmpty()

    fun getSeriesStream(seriesId: String) =
        seriesDao.flowSeries(seriesId)

    fun getPlaylistResultStream(seriesId: String) =
        youtubeDataSource.getSearchResultStream(seriesId)

    suspend fun getPlayLists(seriesId: String, limit: Int) = withContext(Dispatchers.IO) {
        youtubeDataSource.getPlayLists(seriesId, limit)
    }

    suspend fun insertAllSeries(list: List<Series?>) =
        seriesDao.insertAllSeries(list)

    suspend fun getLastUpdateDate() = withContext(Dispatchers.IO) {
        seriesDao.getLastUpdateDate()
    }

    suspend fun updateSeries(forceInit: Boolean) {
        val list = if (forceInit) {
            getRemoteAllSeries()
        } else {
            val lastUpdate = getLastUpdateDate()
            getRemoteUpdatedSeries(lastUpdate)
        }

        insertAllSeries(list)
    }

    private suspend fun getRemoteAllSeries() = withContext(Dispatchers.IO) {
        val list = firestoreDataSource.getAllSeries()
        fetchSeriesThumbnail(list)
    }

    private suspend fun getRemoteUpdatedSeries(lastUpdate: Calendar) = withContext(Dispatchers.IO) {
        val list = firestoreDataSource.getUpdatedSeries(lastUpdate)
        fetchSeriesThumbnail(list)
    }

    private suspend fun fetchSeriesThumbnail(list: List<Series>): List<Series> =
        seriesThumbnailFetcher.invoke(list)


}

