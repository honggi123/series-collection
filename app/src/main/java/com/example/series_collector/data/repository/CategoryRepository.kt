package com.example.series_collector.data.repository

import com.example.series_collector.workers.SeriesThumbnailFetcher
import com.example.series_collector.data.api.model.asDomain
import com.example.series_collector.data.model.*
import com.example.series_collector.data.room.SeriesDao
import com.example.series_collector.data.room.entity.asDomain
import com.example.series_collector.data.source.firebase.FirestoreDataSource
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val seriesDao: SeriesDao,
    private val firestoreDataSource: FirestoreDataSource,
    private val seriesThumbnailFetcher: SeriesThumbnailFetcher
) {

    suspend fun getCategoryContents(): List<ListItem> {
        return firestoreDataSource.getCategorys().map {
            when (it.viewType) {
                ViewType.HORIZONTAL.name -> {
                    Horizontal(
                        title = it.title,
                        items = getSeriesListByCategory(it.categoryId)
                    )
                }

                ViewType.VIEWPAGER.name -> {
                    ViewPager(items = getAds())
                }

                else -> Empty()
            }
        }
    }

    private suspend fun getAds(): List<Ad> {
       return firestoreDataSource.getAds().map { it.asDomain() }
    }

    private suspend fun getSeriesListByCategory(categoryId: String): List<Series> {
        val categoryType: CategoryType = CategoryType.find(categoryId) ?: return emptyList()

        return seriesDao.run {
            val list =
                when (categoryType) {
                    CategoryType.RECENT -> getRecentSeries(limit = 8)
                    CategoryType.POPULAR -> getPopularSeries(limit = 8)
                    CategoryType.FICTION -> getFictionSeries(limit = 16)
                    CategoryType.TRAVEL -> getTravelSeries(limit = 16)
                }
            seriesThumbnailFetcher.invoke(list)
        }.map { it.asDomain() }
    }

}








