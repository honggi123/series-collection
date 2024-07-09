package com.example.data.repository.impl

import com.example.data.repository.UserRepository
import com.example.local.dao.FollowingSeriesDao
import com.example.local.dao.SeriesDao
import com.example.local.entity.FollowingSeriesEntity
import com.example.local.entity.toSeriesList
import com.example.preference.PreferenceManager
import kotlinx.coroutines.flow.map
import java.util.Calendar
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val seriesDao: SeriesDao,
    private val seriesFollowedDao: FollowingSeriesDao,
    private val preferenceManager: PreferenceManager
) : UserRepository {

    override fun isFollowed(seriesId: String) = seriesFollowedDao.isFollowed(seriesId)

    override fun getFollowingSeriesList() = seriesFollowedDao.getFollowingSeriesList()
        .map { it.toSeriesList() }

    override fun getLastUpdateDate() = preferenceManager.getLastUpdateDate()

    override fun updateLastUpdateDate(date: Calendar) = preferenceManager.setLastUpdateDate(date)

    override suspend fun isEmpty() = seriesDao.isEmpty()

    override suspend fun setSeriesFollowed(seriesId: String) {
        val series = FollowingSeriesEntity(seriesId)
        seriesFollowedDao.insertSeriesFollowed(series)
    }

    override suspend fun setSeriesUnFollowed(seriesId: String) =
        seriesFollowedDao.deleteSeriesFollowed(seriesId)
}