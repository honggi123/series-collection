package com.example.data.repository.impl

import androidx.paging.map
import com.example.data.model.toEpisode
import com.example.data.model.toPageInfo
import com.example.data.repository.EpisodeRepository
import com.example.model.episode.PageInfo
import com.example.model.series.Series
import com.example.model.series.Tag
import com.example.model.series.TagType
import com.example.network.model.NetworkEpisode
import com.example.network.source.EpisodeNetworkDataSource
import kotlinx.coroutines.flow.Flow
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

    override fun getTagList(
        series: Series,
        pageInfo: PageInfo
    ): Flow<List<Tag>> = flow {
        val list = listOf(
            Tag(
                TagType.GENRE,
                series.genreType?.displayName
            ),
            Tag(
                TagType.CHANNEL,
                series.channel
            ),
            Tag(
                TagType.TOTAL_PAGE,
                pageInfo.totalResults.toString()
            )
        )
        emit(list)
    }
}