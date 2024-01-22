package com.example.network.model

import com.example.model.category.Ad


data class AdDto(
    val id: Int = 0,
    val url: String = ""
)

fun AdDto.toAd(): Ad {
    return Ad(imgUrl = url)
}

