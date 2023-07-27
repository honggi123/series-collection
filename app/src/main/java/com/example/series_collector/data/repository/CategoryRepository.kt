package com.example.series_collector.data.repository

import com.example.series_collector.data.SeriesThumbnailFetcher
import com.example.series_collector.data.model.mapper.toCategoryContent
import com.example.series_collector.data.model.mapper.asDomain
import com.example.series_collector.data.room.SeriesDao
import com.example.series_collector.data.source.FirestoreDataSource
import com.example.series_collector.ui.home.CategoryType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import java.lang.NullPointerException
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val seriesDao: SeriesDao,
    private val firestoreDataSource: FirestoreDataSource,
    private val seriesThumbnailFetcher: SeriesThumbnailFetcher
) {

    fun getCategoryContentsStream(onComplete: () -> Unit) = flow {
        val categoryContents = firestoreDataSource.getCategorys().map {
            it.toCategoryContent(
                seriesList = getSeriesByCategory(categoryId = it.categoryId)
                    .getOrDefault(emptyList())
                    .asDomain()
            )
        }
        emit(categoryContents)
    }.onCompletion { onComplete() }.flowOn(Dispatchers.IO)

    private suspend fun getSeriesByCategory(categoryId: String) = runCatching {
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
    }
}








