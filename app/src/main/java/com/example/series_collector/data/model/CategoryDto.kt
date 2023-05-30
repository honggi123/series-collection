package com.example.series_collector.data.model

import com.google.gson.annotations.SerializedName

data class CategoryDto(
    @SerializedName("categoryId")
    val categoryId: String = "",
    @SerializedName("title")
    val title: String = "",
)
