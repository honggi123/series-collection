package com.example.data.repository.impl

import com.example.data.repository.CategoryRepository
import com.example.data.source.local.SeriesLocalDataSource
import com.example.data.source.network.CategoryRemoteDataSource
import com.example.model.category.CategoryListItem
import com.example.model.category.Empty
import com.example.model.category.Horizontal
import com.example.model.category.ViewPager
import com.example.model.category.ViewType
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








