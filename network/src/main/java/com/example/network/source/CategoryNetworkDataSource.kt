package com.example.network.source

import com.example.network.model.NetworkAd
import com.example.network.model.NetworkCategory


interface CategoryNetworkDataSource {

    suspend fun getCategorys(): List<NetworkCategory>

    suspend fun getAdList(): List<NetworkAd>

}