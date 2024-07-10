package com.example.data.repository.impl

import com.example.data.repository.SeriesRepository
import com.example.local.dao.SeriesDao
import com.example.local.entity.toSeries
import com.example.model.series.Tag
import com.example.model.series.TagType
import com.example.network.source.EpisodeNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SeriesRepositoryImpl @Inject constructor(
    private val seriesDao: SeriesDao,
    private val episodeNetworkDataSource: EpisodeNetworkDataSource
) : SeriesRepository {

    override fun searchBySeriesName(query: String) = flow {
        val list = seriesDao.getSeriesByQuery(query).map { it.toSeries() }
        emit(list)
    }

    override fun getSeries(seriesId: String) = flow {
        val series = seriesDao.getSeries(seriesId).toSeries()
        emit(series)
    }

    override fun getTagList(
        seriesId: String
    ): Flow<List<Tag>> = flow {
        val series = seriesDao.getSeries(seriesId).toSeries()
        val pageInfo = episodeNetworkDataSource.getPageInfo(seriesId)

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
                pageInfo?.totalResults.toString()
            )
        )
        emit(list)
    }
}




