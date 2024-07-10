package com.example.data.repository.impl

import androidx.paging.map
import com.example.data.model.toEpisode
import com.example.data.model.toPageInfo
import com.example.data.repository.EpisodeRepository
import com.example.network.model.NetworkEpisode
import com.example.network.source.EpisodeNetworkDataSource
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class EpisodeRepositoryImpl @Inject constructor(
    private val episodeNetworkDataSource: EpisodeNetworkDataSource
) : EpisodeRepository {

    override fun getEpisodeList(seriesId: String) =
        episodeNetworkDataSource.getEpisodeListStream(seriesId)
            .map { it.map(NetworkEpisode::toEpisode) }

    override fun getPageInfo(seriesId: String) = flow {
        val pageInfo = episodeNetworkDataSource.getPageInfo(seriesId)
            ?.toPageInfo()
        emit(pageInfo)
    }
}