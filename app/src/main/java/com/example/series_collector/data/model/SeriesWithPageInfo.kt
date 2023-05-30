package com.example.series_collector.data.model

import com.example.series_collector.data.api.PageInfo
import com.example.series_collector.data.room.entity.Series

data class SeriesWithPageInfo(
    val series: Series? = null,
    val pageInfo: PageInfo? = null
)
