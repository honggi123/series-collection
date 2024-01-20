package com.example.series_collector.data.repository

import com.example.series_collector.data.source.local.SeriesLocalDataSource
import com.example.series_collector.data.model.series.Series
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class SeriesRepository @Inject constructor(
    private val seriesLocalDataSource: SeriesLocalDataSource
) {

    fun isFollowed(seriesId: String) = seriesLocalDataSource.isFollowed(seriesId)

    fun getFollowingSeriesList(): Flow<List<Series>> = seriesLocalDataSource.getFollowingSeriesList()

    suspend fun followSeries(seriesId: String) = seriesLocalDataSource.followSeries(seriesId)

    suspend fun unFollowSeries(seriesId: String) = seriesLocalDataSource.unFollowSeries(seriesId)

    suspend fun searchBySeriesName(query: String) = seriesLocalDataSource.searchBySeriesName(query)

    suspend fun getSeries(seriesId: String) = seriesLocalDataSource.getSeries(seriesId)

}




