package com.example.series_collector.data.source.remote

import com.example.series_collector.data.model.category.Ad
import com.example.series_collector.data.model.category.Category

interface CategoryRemoteDataSource {

    suspend fun getCategorys(): List<Category>

    suspend fun getAdList(): List<Ad>

}