package com.example.network.source

import com.example.network.model.NetworkSeries
import java.util.Calendar

interface SeriesNetworkDataSource {

    suspend fun getAllSeries(): List<NetworkSeries>

    suspend fun getUpdatedSeries(lastUpdate: Calendar): List<NetworkSeries>

}