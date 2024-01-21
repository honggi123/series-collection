package com.example.series_collector.data.repository.impl

import com.example.series_collector.data.repository.CategoryRepository
import com.example.series_collector.data.source.local.SeriesLocalDataSource
import com.example.series_collector.model.category.Empty
import com.example.series_collector.model.category.Horizontal
import com.example.series_collector.model.category.CategoryListItem
import com.example.series_collector.model.category.ViewPager
import com.example.series_collector.model.category.ViewType
import com.example.series_collector.data.source.remote.CategoryRemoteDataSource
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryRemoteDataSource: CategoryRemoteDataSource,
    private val seriesLocalDataSource: SeriesLocalDataSource
) : CategoryRepository {

    override suspend fun getCategoryContents(): List<CategoryListItem> {
        return categoryRemoteDataSource.getCategorys().map {
            when (it.viewType) {
                ViewType.HORIZONTAL.name -> {
                    Horizontal(
                        title = it.title,
                        items = seriesLocalDataSource.getSeriesListByCategory(it.id)
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








