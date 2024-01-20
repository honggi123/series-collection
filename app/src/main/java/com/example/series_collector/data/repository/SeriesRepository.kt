package com.example.series_collector.data.repository

import com.example.series_collector.data.local.SeriesLocalDataSource
import com.example.series_collector.data.model.Series
import com.example.series_collector.data.remote.SeriesRemoteDataSource
import com.example.series_collector.local.room.entity.asDomain
import com.example.series_collector.local.SeriesLocalDataSourceImpl
import com.example.series_collector.remote.SeriesRemoteDataSourceImpl
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.*
import javax.inject.Inject

class SeriesRepository @Inject constructor(
    private val seriesLocalDataSource: SeriesLocalDataSource
) {

    fun isFollowed(seriesId: String) = seriesLocalDataSource.isFollowed(seriesId)

    fun getFollowingSeriesList(): Flow<List<Series>> = seriesLocalDataSource.getFollowingSeriesList()
        .map { it.asDomain() }

    suspend fun followSeries(seriesId: String) = seriesLocalDataSource.followSeries(seriesId)

    suspend fun unFollowSeries(seriesId: String) = seriesLocalDataSource.unFollowSeries(seriesId)

    suspend fun searchBySeriesName(query: String) =
        seriesLocalDataSource.searchBySeriesName(query).asDomain()

    suspend fun getSeries(seriesId: String, limit: Int = 1) =
        seriesLocalDataSource.getSeries(seriesId).asDomain()

}




