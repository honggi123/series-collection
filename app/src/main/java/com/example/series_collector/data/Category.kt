package com.example.series_collector.data


data class Category(
    val categoryId: Int = 0,
    val title: String = "",
    val seriesList: List<Series>? = null
)