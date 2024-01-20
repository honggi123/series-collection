package com.example.series_collector.remote.model

import com.example.series_collector.data.model.category.Category

data class CategoryDTO(
    val categoryId: String = "",
    val title: String = "",
    val viewType: String = ""
)

fun CategoryDTO.toCategory(): Category {
    return Category(
        id = this.categoryId,
        title = this.title,
        viewType = this.viewType
    )
}

