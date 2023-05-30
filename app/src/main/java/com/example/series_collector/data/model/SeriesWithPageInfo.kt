package com.example.series_collector.data.model

import com.example.series_collector.data.api.PageInfo

data class SeriesWithPageInfo(
    val name: String,
    val description: String,
    val channel: String,
    val haveCount: Int,
    val genre: Int,
    val thumbnail: String,
    val pageInfo: PageInfo
)
