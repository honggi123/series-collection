package com.example.series_collector.data.repository

import com.example.series_collector.data.Series
import com.example.series_collector.data.SeriesFetcher
import com.example.series_collector.data.room.SeriesDao
import com.example.series_collector.data.source.FirestoreDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val seriesDao: SeriesDao,
    private val firestoreDataSource: FirestoreDataSource,
    private val seriesFetcher: SeriesFetcher
) {

    suspend fun getCategorys() = withContext(Dispatchers.IO) {
        firestoreDataSource.getCategorys()
    }

    suspend fun getSeriesByCategory(categoryId: Int): List<Series> = withContext(Dispatchers.IO) {
        val series = CategoryType.find(categoryId.toString())
        val list = if (series == null) emptyList() else series.getSeriesList(seriesDao)
        fetchSeriesThumbnail(list)
    }

    private suspend fun fetchSeriesThumbnail(list: List<Series>): List<Series> =
        seriesFetcher.fetchSeriesThumbnail(list)
}

enum class CategoryType(
    val label: String
) {
    RECENT(label = "1") {
        override suspend fun getSeriesList(seriesDao: SeriesDao) =
            seriesDao.getRecentSeries()
    },
    POPULAR(label = "2") {
        override suspend fun getSeriesList(seriesDao: SeriesDao) =
            seriesDao.getPopularSeries()
    },
    FICTION(label = "3") {
        override suspend fun getSeriesList(seriesDao: SeriesDao) =
            seriesDao.getFictionSeries()
    },
    TRAVEL(label = "4") {
        override suspend fun getSeriesList(seriesDao: SeriesDao) =
            seriesDao.getTravelSeries()
    };

    abstract suspend fun getSeriesList(seriesDao: SeriesDao): List<Series>

    companion object {
        private val map = values().associateBy(CategoryType::label)
        fun find(type: String) = map[type]
    }
}

