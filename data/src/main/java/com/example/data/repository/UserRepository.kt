package com.example.data.repository

import com.example.model.series.Series
import kotlinx.coroutines.flow.Flow
import java.util.Calendar

interface UserRepository {

    fun isFollowed(seriesId: String): Flow<Boolean>

    fun getFollowingSeriesList(): Flow<List<Series>>

    fun getLastUpdateDate(): Calendar?

    fun updateLastUpdateDate(date: Calendar)

    suspend fun isEmpty(): Boolean

    suspend fun setSeriesFollowed(seriesId: String)

    suspend fun setSeriesUnFollowed(seriesId: String)

}