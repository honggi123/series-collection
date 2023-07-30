package com.example.series_collector.data.model

data class Ad(
    val imgUrl: String
) : ListItem {
    override val viewType: ViewType
        get() = ViewType.AD
}