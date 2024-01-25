package com.example.data.repository.impl

import com.example.data.repository.SeriesRepository
import com.example.local.dao.SeriesDao
import com.example.local.entity.toSeries
import javax.inject.Inject

class SeriesRepositoryImpl @Inject constructor(
    private val seriesDao: SeriesDao,
) : SeriesRepository {

    override suspend fun searchBySeriesName(query: String) =
        seriesDao.getSeriesByQuery(query).map { it.toSeries() }

    override suspend fun getSeries(seriesId: String) =
        seriesDao.getSeries(seriesId).toSeries()

}




