package com.example.data.repository.impl

import com.example.data.repository.SeriesRepository
import com.example.local.dao.SeriesDao
import com.example.local.entity.toSeries
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SeriesRepositoryImpl @Inject constructor(
    private val seriesDao: SeriesDao,
) : SeriesRepository {

    override fun searchBySeriesName(query: String) = flow {
        val list = seriesDao.getSeriesByQuery(query).map { it.toSeries() }
        emit(list)
    }

    override fun getSeries(seriesId: String) = flow {
        val series = seriesDao.getSeries(seriesId).toSeries()
        emit(series)
    }
}




