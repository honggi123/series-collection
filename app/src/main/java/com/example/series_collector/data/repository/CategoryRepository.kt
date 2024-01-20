package com.example.series_collector.data.repository

import com.example.series_collector.data.local.SeriesLocalDataSource
import com.example.series_collector.remote.api.model.asDomain
import com.example.series_collector.data.model.*
import com.example.series_collector.data.remote.CategoryRemoteDataSource
import com.example.series_collector.local.room.entity.asDomain
import com.example.series_collector.remote.CategoryRemoteDataSourceImpl
import com.example.series_collector.local.SeriesLocalDataSourceImpl
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








