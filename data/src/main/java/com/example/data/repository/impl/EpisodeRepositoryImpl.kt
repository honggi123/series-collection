package com.example.data.repository.impl

import com.example.data.repository.EpisodeRepository
import com.example.data.source.network.EpisodeRemoteDataSource
import javax.inject.Inject


class EpisodeRepositoryImpl @Inject constructor(
    private val episodeRemoteDataSource: EpisodeRemoteDataSource
) : EpisodeRepository {
    override fun getEpisodeListStream(seriesId: String) =
        episodeRemoteDataSource.getEpisodeListStream(seriesId)

    override suspend fun getPageInfo(seriesId: String) = episodeRemoteDataSource.getPageInfo(seriesId)

}