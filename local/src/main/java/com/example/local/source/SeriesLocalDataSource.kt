package com.example.local.source

import com.example.local.room.entity.SeriesEntity
import kotlinx.coroutines.flow.Flow
import java.util.Calendar

interface SeriesLocalDataSource {

    fun isFollowed(seriesId: String): Flow<Boolean>

    fun getLastUpdateDate(): Calendar?

    fun setLastUpdateDate(date: Calendar)

    fun getFollowingSeriesList(): Flow<List<SeriesEntity>>

    suspend fun isEmpty(): Boolean

    suspend fun followSeries(seriesId: String)

    suspend fun unFollowSeries(seriesId: String)

    suspend fun getSeriesListByCategory(categoryId: String): List<SeriesEntity>

    suspend fun searchBySeriesName(query: String): List<SeriesEntity>

    suspend fun getSeries(seriesId: String): SeriesEntity

    suspend fun insertSeriesList(list: List<SeriesEntity>)

}
