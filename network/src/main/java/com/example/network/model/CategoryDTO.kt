package com.example.network.model

import com.example.model.category.Category


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

