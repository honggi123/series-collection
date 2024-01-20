package com.example.series_collector.data.repository

import com.example.series_collector.data.model.Series
import com.example.series_collector.data.source.local.room.entity.asDomain
import com.example.series_collector.data.source.local.SeriesLocalDataSource
import com.example.series_collector.data.source.remote.SeriesRemoteDataSource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.*
import javax.inject.Inject

class SeriesRepository @Inject constructor(
    private val seriesLocalDataSource: SeriesLocalDataSource,
    private val seriesRemoteDataSource: SeriesRemoteDataSource
) {

    fun isFollowed(seriesId: String) = seriesLocalDataSource.isFollowed(seriesId)

    fun isEmpty() = seriesLocalDataSource.isEmpty()

    fun getFollowingSeriesList(): Flow<List<Series>> = seriesLocalDataSource.getFollowingSeriesList()
        .map { it.asDomain() }

    suspend fun followSeries(seriesId: String) = seriesLocalDataSource.followSeries(seriesId)

    suspend fun unFollowSeries(seriesId: String) = seriesLocalDataSource.unFollowSeries(seriesId)

    suspend fun searchBySeriesName(query: String) =
        seriesLocalDataSource.searchBySeriesName(query).asDomain()

    suspend fun getSeries(seriesId: String, limit: Int = 1) =
        seriesLocalDataSource.getSeries(seriesId).asDomain()

    suspend fun getRemoteAllSeries() = withContext(Dispatchers.IO) {
        seriesRemoteDataSource.getAllSeries()
    }

    suspend fun getRemoteUpdatedSeries(lastUpdate: Calendar?) =
        withContext(Dispatchers.IO) {
            if (lastUpdate != null) {
                seriesRemoteDataSource.getUpdatedSeries(lastUpdate)
            } else {
                seriesRemoteDataSource.getUpdatedSeries(Calendar.getInstance())
            }
        }
}




