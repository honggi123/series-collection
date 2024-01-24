package com.example.data.model

import com.example.model.category.Ad
import com.example.network.model.NetworkAd

fun NetworkAd.toAd(): Ad {
    return Ad(imgUrl = url)
}

