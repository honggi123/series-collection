package com.example.series_collector.data


data class Category(
    val categoryId: Int = 0,
    val title: String = "",
    var seriesList: List<Series>? = null
)