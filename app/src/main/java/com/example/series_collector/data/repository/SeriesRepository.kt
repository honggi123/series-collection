package com.example.series_collector.data.repository

import android.util.Log
import com.example.series_collector.data.SeriesThumbnailFetcher
import com.example.series_collector.data.api.adpater.ApiResultError
import com.example.series_collector.data.api.adpater.ApiResultException
import com.example.series_collector.data.api.adpater.ApiResultSuccess
import com.example.series_collector.data.model.Series
import com.example.series_collector.data.model.SeriesWithPageInfo
import com.example.series_collector.data.api.model.PageInfo
import com.example.series_collector.data.api.model.asDomain
import com.example.series_collector.data.api.model.asEntity
import com.example.series_collector.data.room.FollowedSeriesDao
import com.example.series_collector.data.room.SeriesDao
import com.example.series_collector.data.room.entity.FollowedSeriesEntity
import com.example.series_collector.data.room.entity.asDomain
import com.example.series_collector.data.source.firebase.FirestoreDataSource
import com.example.series_collector.data.source.preference.PreferenceDataStore
import com.example.series_collector.data.source.youtube.YoutubeDataSource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.*
import javax.inject.Inject

class SeriesRepository @Inject constructor(
    private val seriesDao: SeriesDao,
    private val followedSeriesDao: FollowedSeriesDao,
    private val preferenceDataStore: PreferenceDataStore,
    private val firestoreDataSource: FirestoreDataSource,
    private val youtubeDataSource: YoutubeDataSource,
    private val seriesThumbnailFetcher: SeriesThumbnailFetcher
) {

    fun isFollowed(seriesId: String): Flow<Boolean> {
        return followedSeriesDao.isFollowed(seriesId)
    }

    fun getFollowedSeriesList(): Flow<List<Series>> = flow {
        val list = followedSeriesDao.getFollowedSeriesList()
            .map { it.asDomain() }
        emit(list)
    }.flowOn(Dispatchers.IO)

    suspend fun followSeries(seriesId: String) {
        val seriesFollowedEntity = FollowedSeriesEntity(seriesId)
        followedSeriesDao.insertFollowedSeries(seriesFollowedEntity)
    }

    suspend fun unFollowSeries(seriesId: String) =
        followedSeriesDao.deleteFollowedSeries(seriesId)

    suspend fun searchBySeriesName(query: String): List<Series> {
        return seriesDao.getSeriesByQuery(query).map { result ->
            withContext(Dispatchers.Default) {
                result.asDomain()
            }
        }
    }

    fun getSeriesWithPageInfo(
        seriesId: String,
        limit: Int = 1
    ): Flow<SeriesWithPageInfo> =
        flow {
            val series = seriesDao.getSeries(seriesId)
            val pageInfo = getSeriesPageInfo(series.id, limit)
            emit(
                SeriesWithPageInfo(
                    series = series.asDomain(),
                    pageInfo = pageInfo
                )
            )
        }.flowOn(Dispatchers.IO)

    private suspend fun getSeriesPageInfo(
        seriesId: String,
        limit: Int = 1
    ): PageInfo? {
        val response = youtubeDataSource.getPlayLists(seriesId, limit)

        return when (response) {
            is ApiResultError, is ApiResultException -> null
            is ApiResultSuccess -> response.data.pageInfo
        }
    }

    fun getPlaylistResultStream(playlistId: String) =
        youtubeDataSource.getSearchResultStream(playlistId)

    suspend fun updateSeries(forceInit: Boolean) {

        val list =
            if (forceInit) getRemoteAllSeries().map { it!!.asEntity() }
            else getRemoteUpdatedSeries(lastUpdate = preferenceDataStore.getLastUpdateDate()).map { it.asEntity() }

        seriesDao.run {
            seriesThumbnailFetcher.invoke(list)
                .forEach { series ->
                    insertSeries(series)
                }
        }
        preferenceDataStore.putLastUpdateDate(Calendar.getInstance())
    }

    suspend fun isEmpty() = seriesDao.isEmpty()

    private suspend fun getRemoteAllSeries() = withContext(Dispatchers.IO) {
        firestoreDataSource.getAllSeries()
    }

    private suspend fun getRemoteUpdatedSeries(lastUpdate: Calendar?) =
        withContext(Dispatchers.IO) {
            if (lastUpdate != null) {
                firestoreDataSource.getUpdatedSeries(lastUpdate)
            } else {
                firestoreDataSource.getUpdatedSeries(Calendar.getInstance())
            }
        }
}




