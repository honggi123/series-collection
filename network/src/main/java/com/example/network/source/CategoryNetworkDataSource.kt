package com.example.network.source

import com.example.network.model.NetworkAd
import com.example.network.model.NetworkCategory
import kotlinx.coroutines.flow.Flow


interface CategoryNetworkDataSource {

    fun getCategorys(): Flow<List<NetworkCategory>>

    fun getAdList(): Flow<List<NetworkAd>>
}