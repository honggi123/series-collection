package com.example.series_collector.data.model

import com.example.series_collector.data.room.entity.Series

data class CategoryContent(
    val category: Category? = null,
    val seriesList: List<Series>? = null
)
