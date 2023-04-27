package com.example.series_collector.data.repository

import com.example.series_collector.data.Series
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
    private val youtubeDataSource: YoutubeDataSource
) {

    suspend fun isEmpty() = seriesDao.isEmpty()

    suspend fun getAllSeries() = withContext(Dispatchers.IO) {
        firestoreDataSource.getAllSeries()
    }

    suspend fun getUpdatedSeries(lastUpdate: Calendar) = withContext(Dispatchers.IO) {
        firestoreDataSource.getUpdatedSeries(lastUpdate)
    }

    suspend fun insertSeries(series: Series) = withContext(Dispatchers.IO) {
        seriesDao.insertSeries(series)
    }

    suspend fun insertAllSeries(list: List<Series?>) = withContext(Dispatchers.IO) {
        seriesDao.insertAllSeries(list)
    }

    suspend fun getLastUpdateDate() = withContext(Dispatchers.IO) {
        seriesDao.getLastUpdateDate()
    }

    suspend fun getThumbnailImage(seriesId: String) = withContext(Dispatchers.IO) {
        youtubeDataSource.getThumbNailImage(playListId = seriesId)
    }
}

