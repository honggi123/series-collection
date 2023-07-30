package com.example.series_collector.data.model.mapper

import com.example.series_collector.data.model.CategoryContent
import com.example.series_collector.data.model.dto.CategoryDto
import com.example.series_collector.data.model.Series

fun CategoryDto.toCategoryContent(
    seriesList: List<Series>
): CategoryContent {
    return CategoryContent(
        categoryId = categoryId,
        title = title,
        viewType = viewType,
        seriesList = seriesList
    )
}

