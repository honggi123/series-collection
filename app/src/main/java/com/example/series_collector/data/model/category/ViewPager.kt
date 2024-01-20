package com.example.series_collector.data.model.category

import com.example.series_collector.data.model.category.ViewType

data class ViewPager(
    val items: List<CategoryListItem>
) : CategoryListItem {
    override val viewType: ViewType
        get() = ViewType.VIEWPAGER
}