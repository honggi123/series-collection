package com.example.data.repository

import com.example.model.category.Category
import com.example.model.category.CategoryListItem

interface CategoryRepository {

    suspend fun getCategorys(): List<Category>

    suspend fun getCategoryContent(category: Category): CategoryListItem
}