package com.example.series_collector.data.repository

import com.example.series_collector.data.entitiy.Series
import com.example.series_collector.data.SeriesThumbnailFetcher
import com.example.series_collector.data.entitiy.SeriesWithPageInfo
import com.example.series_collector.data.room.SeriesDao
import com.example.series_collector.data.source.FirestoreDataSource
import com.example.series_collector.data.source.YoutubeDataSource
import com.example.series_collector.ui.base.UiState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import java.util.*
import javax.inject.Inject

class SeriesRepository @Inject constructor(
    private val seriesDao: SeriesDao,
    private val firestoreDataSource: FirestoreDataSource,
    private val youtubeDataSource: YoutubeDataSource,
    private val seriesThumbnailFetcher: SeriesThumbnailFetcher
) {

    suspend fun isEmpty() = seriesDao.isEmpty()

    fun getSeriesWithPageInfoStream(seriesId: String, limit: Int) =
        seriesDao.flowSeries(seriesId).flatMapMerge { series ->
            flow {
                val response = youtubeDataSource.getPlayLists(seriesId, limit)

                if (response.isSuccessful) {
                    val seriesWithPageInfo =
                        SeriesWithPageInfo(series,response.body()!!.pageInfo)
                    emit(UiState.Success(seriesWithPageInfo))
                } else {
                    throw Exception("[${response.code()}] - ${response.raw()}")
                }
            }.catch { e -> UiState.Error(e) }
        }

    fun getPlaylistResultStream(playlistId: String) =
        youtubeDataSource.getSearchResultStream(playlistId)

    suspend fun insertAllSeries(list: List<Series?>) =
        seriesDao.insertAllSeries(list)

    suspend fun getLastUpdateDate() = withContext(Dispatchers.IO) {
        seriesDao.getLastUpdateDate()
    }

    suspend fun updateSeries(forceInit: Boolean) {
        val list = if (forceInit) {
            getRemoteAllSeries()
        } else {
            val lastUpdate = getLastUpdateDate()
            getRemoteUpdatedSeries(lastUpdate)
        }

        insertAllSeries(list)
    }

    private suspend fun getRemoteAllSeries() = withContext(Dispatchers.IO) {
        val list = firestoreDataSource.getAllSeries()
        fetchSeriesThumbnail(list)
    }

    private suspend fun getRemoteUpdatedSeries(lastUpdate: Calendar) = withContext(Dispatchers.IO) {
        val list = firestoreDataSource.getUpdatedSeries(lastUpdate)
        fetchSeriesThumbnail(list)
    }

    private suspend fun fetchSeriesThumbnail(list: List<Series>): List<Series> =
        seriesThumbnailFetcher.invoke(list)


}

