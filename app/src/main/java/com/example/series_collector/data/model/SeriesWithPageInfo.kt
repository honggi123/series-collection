package com.example.series_collector.data.model

import com.example.series_collector.data.model.dto.PageInfo

data class SeriesWithPageInfo(
    val series: Series?,
    val pageInfo : PageInfo?,
)
