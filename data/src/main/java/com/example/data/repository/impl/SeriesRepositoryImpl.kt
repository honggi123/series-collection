package com.example.data.repository.impl

import com.example.data.repository.SeriesRepository
import com.example.local.room.entity.toSeries
import com.example.local.room.entity.toSeriesList
import com.example.local.source.SeriesLocalDataSource
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SeriesRepositoryImpl @Inject constructor(
    private val seriesLocalDataSource: SeriesLocalDataSource
) : SeriesRepository {

    override fun isEmpty() = seriesLocalDataSource.isEmpty()

    override fun isFollowed(seriesId: String) = seriesLocalDataSource.isFollowed(seriesId)

    override fun getLastUpdateDate() = seriesLocalDataSource.getLastUpdateDate()

    override fun getFollowingSeriesList() =
        seriesLocalDataSource.getFollowingSeriesList().map { it.toSeriesList() }

    override suspend fun followSeries(seriesId: String) =
        seriesLocalDataSource.followSeries(seriesId)

    override suspend fun unFollowSeries(seriesId: String) =
        seriesLocalDataSource.unFollowSeries(seriesId)

    override suspend fun searchBySeriesName(query: String) =
        seriesLocalDataSource.searchBySeriesName(query).map { it.toSeries() }

    override suspend fun getSeries(seriesId: String) =
        seriesLocalDataSource.getSeries(seriesId).toSeries()

}




