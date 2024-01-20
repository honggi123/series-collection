package com.example.series_collector.data.repository

import com.example.series_collector.data.source.local.SeriesLocalDataSource
import com.example.series_collector.data.model.category.Empty
import com.example.series_collector.data.model.category.Horizontal
import com.example.series_collector.data.model.category.CategoryListItem
import com.example.series_collector.data.model.category.ViewPager
import com.example.series_collector.data.model.category.ViewType
import com.example.series_collector.data.source.remote.CategoryRemoteDataSource
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val categoryRemoteDataSource: CategoryRemoteDataSource,
    private val seriesLocalDataSource: SeriesLocalDataSource
) {

    suspend fun getCategoryContents(): List<CategoryListItem> {
        return categoryRemoteDataSource.getCategorys().map {
            when (it.viewType) {
                ViewType.HORIZONTAL.name -> {
                    Horizontal(
                        title = it.title,
                        items = seriesLocalDataSource.getSeriesListByCategory(it.categoryId)
                    )
                }

                ViewType.VIEWPAGER.name -> {
                    ViewPager(items = categoryRemoteDataSource.getAdList())
                }

                else -> Empty()
            }
        }
    }
}








