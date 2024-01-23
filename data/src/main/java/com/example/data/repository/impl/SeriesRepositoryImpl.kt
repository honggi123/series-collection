package com.example.data.repository.impl

import com.example.data.repository.SeriesRepository
import com.example.data.source.local.SeriesLocalDataSource
import javax.inject.Inject

class SeriesRepositoryImpl @Inject constructor(
    private val seriesLocalDataSource: SeriesLocalDataSource
) : SeriesRepository {

    override fun isEmpty() = seriesLocalDataSource.isEmpty()

    override fun isFollowed(seriesId: String) = seriesLocalDataSource.isFollowed(seriesId)

    override fun getFollowingSeriesList() = seriesLocalDataSource.getFollowingSeriesList()

    override suspend fun followSeries(seriesId: String) = seriesLocalDataSource.followSeries(seriesId)

    override suspend fun unFollowSeries(seriesId: String) = seriesLocalDataSource.unFollowSeries(seriesId)

    override suspend fun searchBySeriesName(query: String) = seriesLocalDataSource.searchBySeriesName(query)

    override suspend fun getSeries(seriesId: String) = seriesLocalDataSource.getSeries(seriesId)

}




