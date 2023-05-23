package com.example.series_collector.data.entitiy

import com.example.series_collector.data.entitiy.Series


data class Category(
    val categoryId: String = "",
    val title: String = "",
    val seriesList: List<Series>? = null
)
