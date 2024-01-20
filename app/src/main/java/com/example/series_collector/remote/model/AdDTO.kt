package com.example.series_collector.remote.model

import com.example.series_collector.data.model.category.Ad

data class AdDto(
    val id: Int = 0,
    val url: String = ""
)

fun AdDto.toAd(): Ad {
    return Ad(imgUrl = url)
}

