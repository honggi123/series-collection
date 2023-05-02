package com.example.series_collector.data.repository

import com.example.series_collector.data.Series
import com.example.series_collector.data.SeriesFetcher
import com.example.series_collector.data.room.SeriesDao
import com.example.series_collector.data.source.FirestoreDataSource
import com.example.series_collector.data.source.YoutubeDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class SeriesRepository @Inject constructor(
    private val seriesDao: SeriesDao,
    private val firestoreDataSource: FirestoreDataSource,
    private val youtubeDataSource: YoutubeDataSource,
    private val seriesFetcher: SeriesFetcher
) {

    suspend fun isEmpty() = seriesDao.isEmpty()

    suspend fun getAllSeries() = withContext(Dispatchers.IO) {
        val list = firestoreDataSource.getAllSeries()
        seriesFetcher.fetchSeriesThumbnail(list)
    }

    suspend fun getUpdatedSeries(lastUpdate: Calendar) = withContext(Dispatchers.IO) {
        val list = firestoreDataSource.getUpdatedSeries(lastUpdate)
        seriesFetcher.fetchSeriesThumbnail(list)
    }

    suspend fun insertAllSeries(list: List<Series?>) =
        seriesDao.insertAllSeries(list)

    suspend fun getLastUpdateDate() = withContext(Dispatchers.IO) {
        seriesDao.getLastUpdateDate()
    }

}

