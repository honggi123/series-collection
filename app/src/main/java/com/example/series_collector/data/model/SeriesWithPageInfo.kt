package com.example.series_collector.data.model

import com.example.series_collector.data.api.model.PageInfo

data class SeriesWithPageInfo(
    val series: Series?,
    val pageInfo : PageInfo?,
)
