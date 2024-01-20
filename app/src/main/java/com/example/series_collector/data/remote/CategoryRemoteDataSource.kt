package com.example.series_collector.data.remote

import com.example.series_collector.remote.api.model.AdDto
import com.example.series_collector.remote.api.model.CategoryDTO


interface CategoryRemoteDataSource {

    suspend fun getCategorys(): List<CategoryDTO>

    suspend fun getAds(): List<AdDto>

}