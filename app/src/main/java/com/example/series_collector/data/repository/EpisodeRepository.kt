package com.example.series_collector.data.repository

import com.example.series_collector.data.remote.EpisodeRemoteDataSource
import com.example.series_collector.remote.EpisodeRemoteDataSourceImpl
import javax.inject.Inject


class EpisodeRepository @Inject constructor(
    private val episodeRemoteDataSource: EpisodeRemoteDataSource
) {

    suspend fun getEpisodeList(seriesId: String) = episodeRemoteDataSource.getEpisodeList(seriesId)

    fun getEpisodeListStream(seriesId: String) = episodeRemoteDataSource.getEpisodeListStream(seriesId)

}