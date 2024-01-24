package com.example.data.repository.impl

import com.example.data.model.toAd
import com.example.data.repository.CategoryRepository
import com.example.local.room.entity.toSeriesList
import com.example.local.source.SeriesLocalDataSource
import com.example.model.category.CategoryListItem
import com.example.model.category.Empty
import com.example.model.category.Horizontal
import com.example.model.category.ViewPager
import com.example.model.category.ViewType
import com.example.network.source.CategoryNetworkDataSource
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
                        items = seriesLocalDataSource.getSeriesListByCategory(it.categoryId).toSeriesList()
                    )
                }

                ViewType.VIEWPAGER.name -> {
                    ViewPager(items = categoryNetworkDataSource.getAdList().map { it.toAd() })
                }

                else -> Empty()
            }
        }
    }
}








