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

    suspend fun insertSeries(series: Series) =
        seriesDao.insertSeries(series)

    suspend fun insertAllSeries(list: List<Series?>) =
        seriesDao.insertAllSeries(list)

    suspend fun getLastUpdateDate() = withContext(Dispatchers.IO) {
        seriesDao.getLastUpdateDate()
    }

    suspend fun getThumbnailImageUrl(seriesId: String) = withContext(Dispatchers.IO) {
        youtubeDataSource.getThumbnailImageUrl(playListId = seriesId)
    }
}

