package com.example.model.category

data class Ad(
    val position: Int = 0,
    val imgUrl: String,
) : CategoryListItem {
    override val viewType: ViewType
        get() = ViewType.AD
}