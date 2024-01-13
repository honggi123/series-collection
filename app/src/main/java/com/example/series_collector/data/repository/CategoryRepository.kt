package com.example.series_collector.data.repository

import com.example.series_collector.data.SeriesThumbnailFetcher
import com.example.series_collector.data.model.*
import com.example.series_collector.data.mapper.asDomain
import com.example.series_collector.data.room.SeriesDao
import com.example.series_collector.data.source.FirestoreDataSource
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
            when (it.viewType) {
                ViewType.HORIZONTAL.name ->
                    Horizontal(
                        title = it.title,
                        items = getSeriesByCategory(it.categoryId)
                            .getOrDefault(emptyList())
                            .asDomain()
                    )
                ViewType.VIEWPAGER.name -> ViewPager(items = getAds())
                else -> Empty()
            }
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

    private suspend fun getAds(): List<ListItem> {
        val list = mutableListOf<ListItem>()
        seriesDao.getRandomThumbnails(limit = 5)
            .forEach { url ->
                list.add(Ad(imgUrl = url))
            }
        return list
    }

}








