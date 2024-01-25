package com.example.data.repository

import com.example.model.series.Series

interface SeriesRepository {

    suspend fun getSeries(seriesId: String): Series

    suspend fun searchBySeriesName(query: String): List<Series>

}