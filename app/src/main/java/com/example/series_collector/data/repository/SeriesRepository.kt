package com.example.series_collector.data.repository

import com.example.series_collector.model.series.Series
import kotlinx.coroutines.flow.Flow

interface SeriesRepository {

    fun isFollowed(seriesId: String): Flow<Boolean>

    fun getFollowingSeriesList(): Flow<List<Series>>

    suspend fun followSeries(seriesId: String)

    suspend fun unFollowSeries(seriesId: String)

    suspend fun searchBySeriesName(query: String): List<Series>

    suspend fun getSeries(seriesId: String): Series

}