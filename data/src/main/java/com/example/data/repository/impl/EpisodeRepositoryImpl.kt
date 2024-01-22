package com.example.data.repository.impl

import com.example.data.repository.EpisodeRepository
import com.example.data.source.network.EpisodeNetworkDataSource
import javax.inject.Inject


class EpisodeRepositoryImpl @Inject constructor(
    private val episodeNetworkDataSource: EpisodeNetworkDataSource
) : EpisodeRepository {
    override fun getEpisodeListStream(seriesId: String) =
        episodeNetworkDataSource.getEpisodeListStream(seriesId)

    override suspend fun getPageInfo(seriesId: String) = episodeNetworkDataSource.getPageInfo(seriesId)

}