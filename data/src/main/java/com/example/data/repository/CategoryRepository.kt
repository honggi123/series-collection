package com.example.data.repository

import com.example.model.category.CategoryListItem

interface CategoryRepository {

    suspend fun getCategoryContents(): List<CategoryListItem>

}