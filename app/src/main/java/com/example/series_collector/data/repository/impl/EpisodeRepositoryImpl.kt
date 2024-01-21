package com.example.series_collector.data.repository.impl

import com.example.series_collector.data.repository.EpisodeRepository
import com.example.series_collector.data.source.remote.EpisodeRemoteDataSource
import javax.inject.Inject


class EpisodeRepositoryImpl @Inject constructor(
    private val episodeRemoteDataSource: EpisodeRemoteDataSource
) : EpisodeRepository {
    override fun getEpisodeListStream(seriesId: String) =
        episodeRemoteDataSource.getEpisodeListStream(seriesId)

    override suspend fun getPageInfo(seriesId: String) = episodeRemoteDataSource.getPageInfo(seriesId)

}