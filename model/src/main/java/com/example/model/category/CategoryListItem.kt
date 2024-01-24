package com.example.model.category

interface CategoryListItem {
    val viewType: ViewType

    fun getKey() = viewType.hashCode()
}

enum class ViewType {
    HORIZONTAL,
    VIEWPAGER,

    Series,
    AD,
    EMPTY
}

