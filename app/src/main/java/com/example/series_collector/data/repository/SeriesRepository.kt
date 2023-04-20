package com.example.series_collector.data.repository

import com.example.series_collector.data.source.NetworkDataSource
import com.example.series_collector.data.Series
import com.example.series_collector.data.room.SeriesDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class SeriesRepository @Inject constructor(
    private val seriesDao: SeriesDao,
    private val networkDataSource: NetworkDataSource
) {

    suspend fun isEmpty() = seriesDao.isEmpty()

    fun getUpdatedSeries(lastUpdate: Calendar?) = networkDataSource.getUpdatedSeries(lastUpdate)

    suspend fun insertAllSeries(list: List<Series?>) = withContext(Dispatchers.IO) {
        seriesDao.insertAllSeries(list)
    }

    suspend fun getLastUpdatedSeries() = withContext(Dispatchers.IO) {
        seriesDao.getLastUpdatedSeries() }

}