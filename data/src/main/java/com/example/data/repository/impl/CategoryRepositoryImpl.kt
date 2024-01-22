package com.example.data.repository.impl

import com.example.data.repository.CategoryRepository
import com.example.data.source.local.SeriesLocalDataSource
import com.example.data.source.network.CategoryNetworkDataSource
import com.example.model.category.CategoryListItem
import com.example.model.category.Empty
import com.example.model.category.Horizontal
import com.example.model.category.ViewPager
import com.example.model.category.ViewType
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryNetworkDataSource: CategoryNetworkDataSource,
    private val seriesLocalDataSource: SeriesLocalDataSource
) : CategoryRepository {

    override suspend fun getCategoryContents(): List<CategoryListItem> {
        return categoryNetworkDataSource.getCategorys().map {
            when (it.viewType) {
                ViewType.HORIZONTAL.name -> {
                    Horizontal(
                        title = it.title,
                        items = seriesLocalDataSource.getSeriesListByCategory(it.id)
                    )
                }

                ViewType.VIEWPAGER.name -> {
                    ViewPager(items = categoryNetworkDataSource.getAdList())
                }

                else -> Empty()
            }
        }
    }
}








