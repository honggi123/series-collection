package com.example.data.repository

import androidx.paging.PagingData
import com.example.model.episode.Episode
import com.example.model.episode.PageInfo
import com.example.model.series.Series
import com.example.model.series.Tag
import kotlinx.coroutines.flow.Flow

interface EpisodeRepository {

    fun getEpisodeList(seriesId: String): Flow<PagingData<Episode>>

    fun getPageInfo(seriesId: String): Flow<PageInfo?>

    fun getTagList(
        series: Series,
        pageInfo: PageInfo
    ): Flow<List<Tag>>
}