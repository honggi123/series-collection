package com.example.series_collector.data.source.remote

import com.example.series_collector.data.model.category.Ad
import com.example.series_collector.remote.model.CategoryDTO


interface CategoryRemoteDataSource {

    // TODO change return type
    suspend fun getCategorys(): List<CategoryDTO>

    suspend fun getAdList(): List<Ad>

}