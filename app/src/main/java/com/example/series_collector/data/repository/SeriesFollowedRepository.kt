package com.example.series_collector.data.repository

import com.example.series_collector.data.room.entity.SeriesFollowedEntity
import com.example.series_collector.data.room.SeriesFollowedDao
import javax.inject.Inject

class SeriesFollowedRepository @Inject constructor(
    private val seriesFollowedDao: SeriesFollowedDao
) {
    fun isFollowed(seriesId: String) = seriesFollowedDao.isFollowed(seriesId)

    fun getSeriesFollowedList() = seriesFollowedDao.getSeriesInSeriesFollowed()

    suspend fun followSeries(seriesId: String) {
        val seriesFollowedEntity = SeriesFollowedEntity(seriesId)
        seriesFollowedDao.insertSeriesFollowed(seriesFollowedEntity)
    }

    suspend fun unFollowSeries(seriesId: String) =
        seriesFollowedDao.deleteSeriesFollowed(seriesId)
}