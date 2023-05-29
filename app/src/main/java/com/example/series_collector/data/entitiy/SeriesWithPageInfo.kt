package com.example.series_collector.data.entitiy

import com.example.series_collector.data.api.PageInfo

data class SeriesWithPageInfo(
    val series: Series? = null,
    val pageInfo: PageInfo? = null
)
