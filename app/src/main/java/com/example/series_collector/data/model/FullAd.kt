package com.example.series_collector.data.model

data class FullAd(
    val items: List<ListItem>
) : ListItem {
    override val viewType: ViewType
        get() = ViewType.FULLAD
}