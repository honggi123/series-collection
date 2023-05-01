package com.example.series_collector.data.repository

import com.example.series_collector.data.MySeries
import com.example.series_collector.data.room.MySeriesDao
import javax.inject.Inject

class MySeriesRepository @Inject constructor(
    private val mySeriesDao: MySeriesDao
) {
    fun isMySeries(seriesId: String) = mySeriesDao.isMySeries(seriesId)

    suspend fun createMySeries(seriesId: String) {
        val mySeries = MySeries(seriesId)
        mySeriesDao.insertMySeries(mySeries)
    }

    suspend fun removeMySeries(seriesId: String) =
        mySeriesDao.deleteMySeries(seriesId)
}