package com.example.data.repository

import com.example.model.category.CategoryListItem
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun getCategoryContents(): Flow<List<CategoryListItem>>

}