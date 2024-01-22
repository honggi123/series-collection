package com.example.data.source.network

import com.example.model.category.Ad
import com.example.model.category.Category

interface CategoryNetworkDataSource {

    suspend fun getCategorys(): List<Category>

    suspend fun getAdList(): List<Ad>

}