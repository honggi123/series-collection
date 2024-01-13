package com.example.series_collector.data.repository

import com.example.series_collector.data.mapper.asDomain
import com.example.series_collector.data.model.Series
import com.example.series_collector.data.room.entity.SeriesFollowedEntity
import com.example.series_collector.data.room.SeriesFollowedDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SeriesFollowedRepository @Inject constructor(
    private val seriesFollowedDao: SeriesFollowedDao
) {
    fun isFollowed(seriesId: String): Flow<Boolean> {
        return seriesFollowedDao.isFollowed(seriesId)
    }

    fun getFollowedSeriesList(): Flow<List<Series>> {
       return seriesFollowedDao.getFollowedSeriesList()
            .map { it.asDomain() }
    }

    suspend fun followSeries(seriesId: String) {
        val seriesFollowedEntity = SeriesFollowedEntity(seriesId)
        seriesFollowedDao.insertSeriesFollowed(seriesFollowedEntity)
    }

    suspend fun unFollowSeries(seriesId: String) =
        seriesFollowedDao.deleteSeriesFollowed(seriesId)
}