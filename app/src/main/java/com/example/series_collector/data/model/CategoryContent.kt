package com.example.series_collector.data.model

import com.example.series_collector.data.room.entity.SeriesEntity

data class CategoryContent(
    val categoryId: String,
    val title: String,
    val seriesList: List<SeriesEntity>
)
