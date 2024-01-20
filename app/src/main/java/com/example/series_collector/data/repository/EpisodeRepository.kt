package com.example.series_collector.data.repository

import com.example.series_collector.data.source.remote.EpisodeRemoteDataSource
import javax.inject.Inject


class EpisodeRepository @Inject constructor(
    private val episodeRemoteDataSource: EpisodeRemoteDataSource
) {

    suspend fun getEpisodeList(seriesId: String) = episodeRemoteDataSource.getEpisodeList(seriesId)

    fun getEpisodeListStream(seriesId: String) = episodeRemoteDataSource.getEpisodeListStream(seriesId)

}