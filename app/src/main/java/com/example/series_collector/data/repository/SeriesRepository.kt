package com.example.series_collector.data.repository

import com.example.series_collector.data.entitiy.Series
import com.example.series_collector.data.SeriesThumbnailFetcher
import com.example.series_collector.data.api.ApiResult
import com.example.series_collector.data.entitiy.SeriesWithPageInfo
import com.example.series_collector.data.room.SeriesDao
import com.example.series_collector.data.source.FirestoreDataSource
import com.example.series_collector.data.source.YoutubeDataSource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.util.*
import javax.inject.Inject

class SeriesRepository @Inject constructor(
    private val seriesDao: SeriesDao,
    private val firestoreDataSource: FirestoreDataSource,
    private val youtubeDataSource: YoutubeDataSource,
    private val seriesThumbnailFetcher: SeriesThumbnailFetcher
) {

    suspend fun isEmpty() = seriesDao.isEmpty()

    fun getSeriesWithPageInfoStream(
        seriesId: String,
        limit: Int
    ): Flow<ApiResult<SeriesWithPageInfo>> {
        return seriesDao.flowSeries(seriesId).flatMapMerge { series ->
            flow {
                emit(getSeriesWithPageInfo(series, seriesId, limit))
            }.catch { e -> emit(ApiResult.Error(exception = e)) }
        }
    }

    private suspend fun getSeriesWithPageInfo(
        series: Series,
        seriesId: String,
        limit: Int
    ): ApiResult<SeriesWithPageInfo> {
        val response = youtubeDataSource.getPlayLists(seriesId, limit)

        if (!response.isSuccessful) throw HttpException(response)

        val seriesWithPageInfo =
            SeriesWithPageInfo(
                series = series,
                pageInfo = response.body()?.pageInfo ?: return ApiResult.Empty
            )
        return ApiResult.Success(seriesWithPageInfo)
    }

    fun getPlaylistResultStream(playlistId: String) =
        youtubeDataSource.getSearchResultStream(playlistId)


    suspend fun updateSeries(forceInit: Boolean) {
        seriesDao.run {
            val list = if (forceInit) {
                getRemoteAllSeries()
            } else {
                getRemoteUpdatedSeries(getLastUpdateDate())
            }
            seriesThumbnailFetcher(list)

            insertAllSeries(list)
        }
    }

    private suspend fun getRemoteAllSeries() = withContext(Dispatchers.IO) {
        firestoreDataSource.getAllSeries()
    }

    private suspend fun getRemoteUpdatedSeries(lastUpdate: Calendar) = withContext(Dispatchers.IO) {
        firestoreDataSource.getUpdatedSeries(lastUpdate)
    }

}

