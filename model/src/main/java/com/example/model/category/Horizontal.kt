package com.example.model.category

data class Horizontal(
    val title: String,
    val items: List<CategoryListItem>,
) : CategoryListItem {
    override val viewType: ViewType
        get() = ViewType.HORIZONTAL
}