package com.example.series_collector.data.model

data class ViewPager(
    val items: List<ListItem>
) : ListItem {
    override val viewType: ViewType
        get() = ViewType.VIEWPAGER
}