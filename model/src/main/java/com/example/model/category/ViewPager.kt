package com.example.model.category

data class ViewPager(
    val items: List<CategoryListItem>
) : CategoryListItem {
    override val viewType: ViewType
        get() = ViewType.VIEWPAGER
}