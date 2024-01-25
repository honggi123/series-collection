package com.example.data.repository

import com.example.model.series.Series
import kotlinx.coroutines.flow.Flow
import java.util.Calendar

interface SeriesRepository {


    fun isFollowed(seriesId: String): Flow<Boolean>

    fun getLastUpdateDate(): Calendar?

    fun updatelastUpdateDate(date: Calendar)

    fun getFollowingSeriesList(): Flow<List<Series>>

    suspend fun isEmpty(): Boolean

    suspend fun getSeries(seriesId: String): Series

    suspend fun followSeries(seriesId: String)

    suspend fun unFollowSeries(seriesId: String)

    suspend fun searchBySeriesName(query: String): List<Series>

}