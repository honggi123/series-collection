package com.example.data.repository.impl

import com.example.data.model.toAd
import com.example.data.repository.CategoryRepository
import com.example.local.dao.SeriesDao
import com.example.local.entity.SeriesEntity
import com.example.local.entity.toSeries
import com.example.model.category.Category
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

    override fun getCategorys(): Flow<List<Category>> = flow {
        val categorys = categoryNetworkDataSource.getCategorys()
            .map { Category(it.categoryId, it.title, it.viewType) }

        emit(categorys)
    }

    override suspend fun getCategoryContent(category: Category): CategoryListItem {
        return when (category.viewType) {
            ViewType.HORIZONTAL.name -> {
                Horizontal(
                    title = category.title,
                    items = getSeriesListByCategory(category.id)
                        .map { it.toSeries() }
                )
            }

            ViewType.VIEWPAGER.name -> {
                ViewPager(items = categoryNetworkDataSource.getAdList()
                    .map { it.toAd() })
            }

            else -> Empty()
        }
    }

    private suspend fun getSeriesListByCategory(categoryId: String): List<SeriesEntity> {
        val categoryType: CategoryType = CategoryType.find(categoryId) ?: throw NullPointerException()

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








