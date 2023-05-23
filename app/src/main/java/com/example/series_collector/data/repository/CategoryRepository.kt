package com.example.series_collector.data.repository

import com.example.series_collector.data.entitiy.Series
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

    suspend fun getSeriesByCategory(categoryId: String): List<Series> =
        withContext(Dispatchers.IO) {
            val categoryType: CategoryType? = CategoryType.find(categoryId)
            seriesDao.run {
                val list = when (categoryType) {
                    CategoryType.RECENT -> getRecentSeries()
                    CategoryType.POPULAR -> getPopularSeries()
                    CategoryType.FICTION -> getFictionSeries()
                    CategoryType.TRAVEL -> getTravelSeries()
                    null -> emptyList()
                }
                fetchSeriesThumbnail(list)
            }
        }


    private suspend fun fetchSeriesThumbnail(list: List<Series>): List<Series> =
        seriesFetcher.fetchSeriesThumbnail(list)
}

enum class CategoryType(
    val id: String
) {
    RECENT(id = "category_recent"), POPULAR(id = "category_popular"),
    FICTION(id = "category_fiction"), TRAVEL(id = "category_travel");

    companion object {
        private val map = values().associateBy(CategoryType::id)
        fun find(id: String) = map[id]
    }
}


