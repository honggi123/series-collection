package com.example.series_collector.data.source.remote

import com.example.series_collector.model.series.Series
import java.util.Calendar

interface SeriesRemoteDataSource {

    suspend fun getAllSeries(): List<Series>

    suspend fun getUpdatedSeries(lastUpdate: Calendar): List<Series>

}