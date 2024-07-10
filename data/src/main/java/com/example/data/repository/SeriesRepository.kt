package com.example.data.repository

import com.example.model.series.Series
import com.example.model.series.Tag
import kotlinx.coroutines.flow.Flow

interface SeriesRepository {

    fun searchBySeriesName(query: String): Flow<List<Series>>

    fun getSeries(seriesId: String): Flow<Series>

    fun getTagList(seriesId: String): Flow<List<Tag>>
}