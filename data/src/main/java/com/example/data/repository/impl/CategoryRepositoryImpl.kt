package com.example.data.repository.impl

import com.example.data.model.toAd
import com.example.data.repository.CategoryRepository
import com.example.local.dao.SeriesDao
import com.example.local.entity.SeriesEntity
import com.example.local.entity.toSeriesList
import com.example.model.category.CategoryListItem
import com.example.model.category.CategoryType
import com.example.model.category.Empty
import com.example.model.category.Horizontal
import com.example.model.category.ViewPager
import com.example.model.category.ViewType
import com.example.network.source.CategoryNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryNetworkDataSource: CategoryNetworkDataSource,
    private val seriesDao: SeriesDao,
) : CategoryRepository {

    override fun getCategoryContents(): Flow<List<CategoryListItem>> = flow {
        val list = categoryNetworkDataSource.getCategorys().map {
            when (it.viewType) {
                ViewType.HORIZONTAL.name -> {
                    Horizontal(
                        title = it.title,
                        items = getSeriesListByCategory(it.categoryId).toSeriesList()
                    )
                }

                ViewType.VIEWPAGER.name -> {
                    ViewPager(items = categoryNetworkDataSource.getAdList().map { it.toAd() })
                }

                else -> Empty()
            }
        }
        emit(list)
    }

    private suspend fun getSeriesListByCategory(categoryId: String): List<SeriesEntity> {
        val categoryType: CategoryType = CategoryType.find(categoryId) ?: return emptyList()

        return seriesDao.run {
            when (categoryType) {
                CategoryType.RECENT -> getRecentSeries(limit = 8)
                CategoryType.POPULAR -> getPopularSeries(limit = 8)
                CategoryType.FICTION -> getFictionSeries(limit = 16)
                CategoryType.TRAVEL -> getTravelSeries(limit = 16)
            }
        }
    }
}








