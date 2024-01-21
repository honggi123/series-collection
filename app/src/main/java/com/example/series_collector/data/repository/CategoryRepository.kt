package com.example.series_collector.data.repository

import com.example.series_collector.model.category.CategoryListItem

interface CategoryRepository {

    suspend fun getCategoryContents(): List<CategoryListItem>

}