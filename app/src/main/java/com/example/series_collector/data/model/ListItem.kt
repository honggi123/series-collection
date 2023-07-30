package com.example.series_collector.data.model

interface ListItem {
    val viewType: ViewType
}

enum class ViewType {
    HORIZONTAL,
    FULLAD,

    Series,
    AD,
    EMPTY
}

