package com.example.series_collector.data


data class Category(
    val categoryId: String = "",
    val title: String = "",
    val seriesList: List<Series>? = null
)
