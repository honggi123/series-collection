package com.example.series_collector.data.api.model

import com.example.series_collector.data.model.Ad
import com.google.firebase.firestore.DocumentId

data class AdDto(
    val id: Int = 0,
    val url: String = ""
)

fun AdDto.asDomain(): Ad {
    return Ad(imgUrl = url)
}

