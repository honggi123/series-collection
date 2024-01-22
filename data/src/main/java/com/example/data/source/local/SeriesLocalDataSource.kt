package com.example.data.source.local

import com.example.model.series.Series
import kotlinx.coroutines.flow.Flow

interface SeriesLocalDataSource {

    fun isEmpty(): Boolean

    fun isFollowed(seriesId: String): Flow<Boolean>

    fun getFollowingSeriesList(): Flow<List<Series>>

    suspend fun followSeries(seriesId: String)

    suspend fun unFollowSeries(seriesId: String)

    suspend fun getSeriesListByCategory(categoryId: String): List<Series>

    suspend fun searchBySeriesName(query: String): List<Series>

    suspend fun getSeries(seriesId: String): Series

    suspend fun insertSeries(series: Series)

}
