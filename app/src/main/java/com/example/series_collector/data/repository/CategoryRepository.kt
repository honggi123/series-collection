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

    suspend fun getSeriesByCategory(categoryId: String): List<Series> = withContext(Dispatchers.IO) {
        val categoryType = CategoryType.find(categoryId)
        val list = if (categoryType == null) emptyList() else categoryType.getSeriesList(seriesDao)
        fetchSeriesThumbnail(list)
    }

    private suspend fun fetchSeriesThumbnail(list: List<Series>): List<Series> =
        seriesFetcher.fetchSeriesThumbnail(list)
}

enum class CategoryType(
    val id: String
) {
    RECENT(id = "category_recent") {
        override suspend fun getSeriesList(seriesDao: SeriesDao) =
            seriesDao.getRecentSeries()
    },
    POPULAR(id = "category_popular") {
        override suspend fun getSeriesList(seriesDao: SeriesDao) =
            seriesDao.getPopularSeries()
    },
    FICTION(id = "category_fiction") {
        override suspend fun getSeriesList(seriesDao: SeriesDao) =
            seriesDao.getFictionSeries()
    },
    TRAVEL(id = "category_travel") {
        override suspend fun getSeriesList(seriesDao: SeriesDao) =
            seriesDao.getTravelSeries()
    };

    abstract suspend fun getSeriesList(seriesDao: SeriesDao): List<Series>

    companion object {
        private val map = values().associateBy(CategoryType::id)
        fun find(type: String) = map[type]
    }
}


