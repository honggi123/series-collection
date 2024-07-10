package com.example.data.repository.impl

import com.example.data.repository.CategoryRepository
import com.example.local.dao.SeriesDao
import com.example.local.entity.SeriesEntity
import com.example.model.category.Category
import com.example.model.category.CategoryListItem
import com.example.model.category.CategoryType
import com.example.network.source.CategoryNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryNetworkDataSource: CategoryNetworkDataSource,
    private val seriesDao: SeriesDao,
) : CategoryRepository {

    override fun getCategorys(): Flow<List<Category>> {
        return categoryNetworkDataSource.getCategorys()
            .map { it.map { Category(it.categoryId, it.title, it.viewType) } }
    }

    override suspend fun getCategoryContent(category: Category): CategoryListItem {
       TODO()
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








