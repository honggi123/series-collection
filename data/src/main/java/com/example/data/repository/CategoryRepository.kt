package com.example.data.repository

import com.example.model.category.Category
import com.example.model.category.CategoryListItem
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun getCategorys(): Flow<List<Category>>

    suspend fun getCategoryContent(category: Category): CategoryListItem
}