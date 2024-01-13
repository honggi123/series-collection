package com.example.series_collector.data.repository

import com.example.series_collector.data.room.entity.SeriesEntity
import com.example.series_collector.data.SeriesThumbnailFetcher
import com.example.series_collector.data.api.ApiResponse
import com.example.series_collector.data.api.ApiResultError
import com.example.series_collector.data.api.ApiResultException
import com.example.series_collector.data.api.ApiResultSuccess
import com.example.series_collector.data.model.Series
import com.example.series_collector.data.model.SeriesWithPageInfo
import com.example.series_collector.data.mapper.asDomain
import com.example.series_collector.data.model.dto.PageInfo
import com.example.series_collector.data.model.dto.PlayListsDto
import com.example.series_collector.data.room.SeriesDao
import com.example.series_collector.data.source.FirestoreDataSource
import com.example.series_collector.data.source.youtube.YoutubeDataSource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.io.IOException
import java.util.*
import javax.inject.Inject

class SeriesRepository @Inject constructor(
    private val seriesDao: SeriesDao,
    private val firestoreDataSource: FirestoreDataSource,
    private val youtubeDataSource: YoutubeDataSource,
    private val seriesThumbnailFetcher: SeriesThumbnailFetcher
) {

    suspend fun searchBySeriesName(query: String): List<Series> {
        return seriesDao.getSeriesByQuery(query).map { result ->
            withContext(Dispatchers.Default) {
                result.asDomain()
            }
        }
    }

    fun getSeriesWithPageInfoStream(
        seriesId: String,
        limit: Int = 1
    ): Flow<SeriesWithPageInfo> =
        flow {
            val series = seriesDao.getSeries(seriesId)
            val pageInfo = getSeriesPageInfo(series.seriesId, limit)
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




