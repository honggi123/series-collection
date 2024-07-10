package com.example.network.source

import com.example.network.model.NetworkSeries
import kotlinx.coroutines.flow.Flow
import java.util.Calendar

interface SeriesNetworkDataSource {

    fun getAllSeries(): Flow<List<NetworkSeries>>

    fun getUpdatedSeries(lastUpdate: Calendar): Flow<List<NetworkSeries>>

}