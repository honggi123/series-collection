package com.example.series_collector.data.repository

import android.util.Log
import com.example.series_collector.data.room.entity.SeriesEntity
import com.example.series_collector.data.SeriesThumbnailFetcher
import com.example.series_collector.data.api.ApiResult
import com.example.series_collector.data.model.SeriesWithPageInfo
import com.example.series_collector.data.model.mapper.asDomain
import com.example.series_collector.data.model.mapper.toSeriesWithPageInfo
import com.example.series_collector.data.room.SeriesDao
import com.example.series_collector.data.source.FirestoreDataSource
import com.example.series_collector.data.source.YoutubeDataSource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.util.*
import javax.inject.Inject

class SeriesRepository @Inject constructor(
    private val seriesDao: SeriesDao,
    private val firestoreDataSource: FirestoreDataSource,
    private val youtubeDataSource: YoutubeDataSource,
    private val seriesThumbnailFetcher: SeriesThumbnailFetcher
) {

    suspend fun searchBySeriesName(query: String) =
        seriesDao.getSeriesByQuery(query).asDomain()

    fun getSeriesWithPageInfoStream(
        seriesId: String,
        limit: Int = 1
    ): Flow<ApiResult<SeriesWithPageInfo>> {
        return seriesDao.flowSeries(seriesId).flatMapMerge { series ->
            flow {
                emit(getSeriesWithPageInfo(series, seriesId, limit))
            }.catch { e ->
                val networkResponse = when (e) {
                    is IOException -> ApiResult.NetworkError(e)
                    else -> ApiResult.Unexpected(e)
                }
                emit(networkResponse)
            }
        }
    }

    private suspend fun getSeriesWithPageInfo(
        seriesEntity: SeriesEntity,
        seriesId: String,
        limit: Int = 1
    ): ApiResult<SeriesWithPageInfo> {
        val response = youtubeDataSource.getPlayLists(seriesId, limit)

        if (!response.isSuccessful) return ApiResult.Failure(
            code = response.code(),
            msg = response.message()
        )

        val seriesWithPageInfo =
            seriesEntity.toSeriesWithPageInfo(
                pageInfo = response.body()?.pageInfo
            )

        return ApiResult.Success(seriesWithPageInfo)
    }

    fun getPlaylistResultStream(playlistId: String) =
        youtubeDataSource.getSearchResultStream(playlistId)

    suspend fun updateSeries(forceInit: Boolean) {
        seriesDao.run {
            val list =
                if (forceInit) getRemoteAllSeries()
                else getRemoteUpdatedSeries(lastUpdate = getLastUpdateDate())

            seriesThumbnailFetcher(list)
                .forEach { series ->
                    insertSeries(series)
                }
        }
    }

    suspend fun isEmpty() = seriesDao.isEmpty()

    private suspend fun getRemoteAllSeries() = withContext(Dispatchers.IO) {
        firestoreDataSource.getAllSeries()
    }

    private suspend fun getRemoteUpdatedSeries(lastUpdate: Calendar) = withContext(Dispatchers.IO) {
        firestoreDataSource.getUpdatedSeries(lastUpdate)
    }
}




