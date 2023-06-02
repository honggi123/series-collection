package com.example.series_collector.data.repository

import android.util.Log
import com.example.series_collector.data.SeriesThumbnailFetcher
import com.example.series_collector.data.model.Series
import com.example.series_collector.data.model.mapper.toCategoryContent
import com.example.series_collector.data.model.mapper.asDomain
import com.example.series_collector.data.room.SeriesDao
import com.example.series_collector.data.room.entity.SeriesEntity
import com.example.series_collector.data.source.FirestoreDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.withContext
import java.lang.NullPointerException
import javax.inject.Inject

enum class CategoryType(
    val categoryId: String
) {
    RECENT(categoryId = "category_recent"), POPULAR(categoryId = "category_popular"),
    FICTION(categoryId = "category_fiction"), TRAVEL(categoryId = "category_travel");

    companion object {
        private val map = values().associateBy(CategoryType::categoryId)
        fun find(id: String) = map[id]
    }
}

class CategoryRepository @Inject constructor(
    private val seriesDao: SeriesDao,
    private val firestoreDataSource: FirestoreDataSource,
    private val seriesThumbnailFetcher: SeriesThumbnailFetcher
) {

    fun getCategoryContentsStream(onComplete: () -> Unit) = flow {
        val categoryContents = firestoreDataSource.getCategorys().map {
            it.toCategoryContent(
                seriesList = getSeriesByCategory(categoryId = it.categoryId)?.asDomain()
                    ?: emptyList()
            )
        }
        emit(categoryContents)
    }.onCompletion { onComplete() }.flowOn(Dispatchers.IO)

    private suspend fun getSeriesByCategory(categoryId: String): List<SeriesEntity>? = runCatching {
        val categoryType: CategoryType = CategoryType.find(categoryId)
            ?: throw NullPointerException()

        seriesDao.run {
            val list =
                when (categoryType) {
                    CategoryType.RECENT -> getRecentSeries(limit = 8)
                    CategoryType.POPULAR -> getPopularSeries(limit = 8)
                    CategoryType.FICTION -> getFictionSeries(limit = 16)
                    CategoryType.TRAVEL -> getTravelSeries(limit = 16)
                }
            seriesThumbnailFetcher(list)
        }
    }.getOrNull()
}







