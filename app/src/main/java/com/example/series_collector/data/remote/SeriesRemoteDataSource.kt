package com.example.series_collector.data.remote

import com.example.series_collector.remote.api.model.SeriesDTO
import kotlinx.coroutines.tasks.await
import java.util.Calendar

interface SeriesRemoteDataSource {

    suspend fun getAllSeries(): List<SeriesDTO>

    suspend fun getUpdatedSeries(lastUpdate: Calendar): List<SeriesDTO>

}