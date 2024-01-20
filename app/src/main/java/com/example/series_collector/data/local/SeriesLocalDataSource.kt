package com.example.series_collector.data.local

import com.example.series_collector.data.model.CategoryType
import com.example.series_collector.local.room.entity.FollowedSeriesEntity
import com.example.series_collector.local.room.entity.SeriesEntity
import kotlinx.coroutines.flow.Flow

interface SeriesLocalDataSource {

    fun isFollowed(seriesId: String): Flow<Boolean>

    fun isEmpty(): Boolean

    fun getFollowingSeriesList(): Flow<List<SeriesEntity>>

    suspend fun followSeries(seriesId: String)

    suspend fun unFollowSeries(seriesId: String)

    suspend fun getSeriesListByCategory(categoryId: String): List<SeriesEntity>

    suspend fun searchBySeriesName(query: String): List<SeriesEntity>

    suspend fun getSeries(seriesId: String): SeriesEntity

    suspend fun insertSeries(seriesEntity: SeriesEntity)

}
