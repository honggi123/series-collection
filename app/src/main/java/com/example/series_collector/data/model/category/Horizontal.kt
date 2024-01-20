package com.example.series_collector.data.model.category

data class Horizontal(
    val title: String,
    val items: List<CategoryListItem>
) : CategoryListItem {
    override val viewType: ViewType
        get() = ViewType.HORIZONTAL
}