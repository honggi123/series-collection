package com.example.series_collector.data.repository

import com.example.series_collector.data.api.adpater.ApiResponse
import com.example.series_collector.data.model.Series
import com.example.series_collector.data.api.model.PlayListsDto
import com.example.series_collector.data.room.FollowedSeriesDao
import com.example.series_collector.data.room.SeriesDao
import com.example.series_collector.data.room.entity.FollowedSeriesEntity
import com.example.series_collector.data.room.entity.asDomain
import com.example.series_collector.data.source.firebase.FirestoreDataSource
import com.example.series_collector.data.source.youtube.YoutubeDataSource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.*
import javax.inject.Inject

class SeriesRepository @Inject constructor(
    private val seriesDao: SeriesDao,
    private val followedSeriesDao: FollowedSeriesDao,
    private val firestoreDataSource: FirestoreDataSource,
    private val youtubeDataSource: YoutubeDataSource,
) {

    fun isFollowed(seriesId: String) = followedSeriesDao.isFollowed(seriesId)

    fun isEmpty() = seriesDao.isEmpty()

    fun getFollowingSeriesList(): Flow<List<Series>> = flow {
        val list = followedSeriesDao.getFollowedSeriesList()
            .map { it.asDomain() }
        emit(list)
    }.flowOn(Dispatchers.IO)

    suspend fun followSeries(seriesId: String) {
        val seriesFollowedEntity = FollowedSeriesEntity(seriesId)
        followedSeriesDao.insertFollowedSeries(seriesFollowedEntity)
    }

    suspend fun unFollowSeries(seriesId: String) {
        followedSeriesDao.deleteFollowedSeries(seriesId)
    }

    suspend fun searchBySeriesName(query: String): List<Series> {
        return seriesDao.getSeriesByQuery(query).map { result ->
            withContext(Dispatchers.IO) {
                result.asDomain()
            }
        }
    }

    suspend fun getSeries(seriesId: String, limit: Int = 1): Series {
        return seriesDao.getSeries(seriesId).asDomain()
    }

    suspend fun getPlayList(seriesId: String, limit: Int = 1): ApiResponse<PlayListsDto> {
        return youtubeDataSource.getPlayLists(seriesId, limit)
    }

    suspend fun getRemoteAllSeries() = withContext(Dispatchers.IO) {
        firestoreDataSource.getAllSeries()
    }

    suspend fun getRemoteUpdatedSeries(lastUpdate: Calendar?) =
        withContext(Dispatchers.IO) {
            if (lastUpdate != null) {
                firestoreDataSource.getUpdatedSeries(lastUpdate)
            } else {
                firestoreDataSource.getUpdatedSeries(Calendar.getInstance())
            }
        }
}




