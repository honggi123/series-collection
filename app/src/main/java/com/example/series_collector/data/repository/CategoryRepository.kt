package com.example.series_collector.data.repository

import com.example.series_collector.data.source.remote.api.model.asDomain
import com.example.series_collector.data.model.*
import com.example.series_collector.data.source.local.room.entity.asDomain
import com.example.series_collector.data.source.remote.CategoryRemoteDataSource
import com.example.series_collector.data.source.local.SeriesLocalDataSource
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val categoryRemoteDataSource: CategoryRemoteDataSource,
    private val seriesLocalDataSource: SeriesLocalDataSource
) {

    suspend fun getCategoryContents(): List<ListItem> {
        return categoryRemoteDataSource.getCategorys().map {
            when (it.viewType) {
                ViewType.HORIZONTAL.name -> {
                    Horizontal(
                        title = it.title,
                        items = seriesLocalDataSource.getSeriesListByCategory(it.categoryId)
                            .map { it.asDomain() }
                    )
                }

                ViewType.VIEWPAGER.name -> {
                    ViewPager(items = categoryRemoteDataSource.getAds().map { it.asDomain() })
                }

                else -> Empty()
            }
        }
    }
}








