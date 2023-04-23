package com.example.series_collector.data.repository

import com.example.series_collector.data.source.NetworkDataSource
import com.example.series_collector.data.Series
import com.example.series_collector.data.room.SeriesDao
import com.example.series_collector.utils.CATEGORY_FICTION
import com.example.series_collector.utils.CATEGORY_POPULAR
import com.example.series_collector.utils.CATEGORY_RECENT
import com.example.series_collector.utils.CATEGORY_TRAVEL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class SeriesRepository @Inject constructor(
    private val seriesDao: SeriesDao,
    private val networkDataSource: NetworkDataSource
) {

    suspend fun isEmpty() = seriesDao.isEmpty()

    suspend fun getUpdatedSeries(lastUpdate: Calendar) = withContext(Dispatchers.IO) {
        networkDataSource.getUpdatedSeries(lastUpdate)
    }

    suspend fun getCategorys() = withContext(Dispatchers.IO) {
        networkDataSource.getCategorys()
    }

    suspend fun getCategoryList(categoryID: Int) = withContext(Dispatchers.IO) {
        when (categoryID) {
            CATEGORY_RECENT -> {
                seriesDao.getRecentSeries()
            }
            CATEGORY_POPULAR -> {
                seriesDao.getPopularSeries()
            }
            CATEGORY_FICTION -> {
                seriesDao.getFictionSeries()
            }
            CATEGORY_TRAVEL -> {
                seriesDao.getTravelSeries()
            }
            else -> {
                seriesDao.getRecentSeries()
            }
        }
    }


    suspend fun insertAllSeries(list: List<Series?>) = withContext(Dispatchers.IO) {
        seriesDao.insertAllSeries(list)
    }

    suspend fun getLastUpdateDate() = withContext(Dispatchers.IO) {
        seriesDao.getLastUpdateDate()
    }

}