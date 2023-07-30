package com.example.series_collector.data.model

data class CategoryContent(
    val categoryId: String,
    val title: String,
    val viewType: String,
    val seriesList: List<Series>
)
