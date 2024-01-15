package com.example.series_collector.data.api.model

import com.example.series_collector.data.model.Ad

data class AdDto(
    val id: Int,
    val url: String
)

fun AdDto.asDomain(): Ad {
    return Ad(imgUrl = url)
}

