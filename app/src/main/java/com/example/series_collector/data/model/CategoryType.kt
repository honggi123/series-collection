package com.example.series_collector.data.model

enum class CategoryType(
    val categoryId: String
) {
    RECENT(categoryId = "category_recent"),
    POPULAR(categoryId = "category_popular"),
    FICTION(categoryId = "category_fiction"),
    TRAVEL(categoryId = "category_travel");

    companion object {
        private val map = values().associateBy(CategoryType::categoryId)
        fun find(id: String) = map[id]
    }
}
