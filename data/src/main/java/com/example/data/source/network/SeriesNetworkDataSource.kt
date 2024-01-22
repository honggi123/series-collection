package com.example.data.source.network

import com.example.model.series.Series
import java.util.Calendar

interface SeriesNetworkDataSource {

    suspend fun getAllSeries(): List<Series>

    suspend fun getUpdatedSeries(lastUpdate: Calendar): List<Series>

}