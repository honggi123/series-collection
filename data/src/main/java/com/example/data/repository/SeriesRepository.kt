package com.example.data.repository

import com.example.model.series.Series
import kotlinx.coroutines.flow.Flow

interface SeriesRepository {

    fun searchBySeriesName(query: String): Flow<List<Series>>

    fun getSeries(seriesId: String): Flow<Series>
}