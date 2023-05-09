package com.example.series_collector.data.repository

import android.util.Log
import com.example.series_collector.data.Series
import com.example.series_collector.data.SeriesFetcher
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
    private val seriesFetcher: SeriesFetcher
) {

    suspend fun isEmpty() = seriesDao.isEmpty()

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
        seriesFetcher.fetchSeriesThumbnail(list)


}

