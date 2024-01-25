package com.example.local.source.impl

import com.example.local.preference.PreferenceManager
import com.example.local.room.FollowingSeriesDao
import com.example.local.room.SeriesDao
import com.example.local.room.entity.FollowingSeriesEntity
import com.example.local.room.entity.SeriesEntity
import com.example.local.source.SeriesLocalDataSource
import com.example.model.category.CategoryType
import kotlinx.coroutines.flow.Flow
import java.util.Calendar
import javax.inject.Inject

class SeriesLocalDataSourceImpl @Inject constructor(
    private val seriesDao: SeriesDao,
    private val followingSeriesDao: FollowingSeriesDao,
    private val preferenceManager: PreferenceManager
) : SeriesLocalDataSource {

    override fun isFollowed(seriesId: String) = followingSeriesDao.isFollowed(seriesId)

    override fun getLastUpdateDate() = preferenceManager.getLastUpdateDate()

    override fun setLastUpdateDate(date: Calendar) = preferenceManager.putLastUpdateDate(date)

    override fun getFollowingSeriesList(): Flow<List<SeriesEntity>> =
        followingSeriesDao.getFollowingSeriesList()

    override suspend fun isEmpty() = seriesDao.isEmpty()

    override suspend fun followSeries(seriesId: String) {
        val seriesFollowedEntity = FollowingSeriesEntity(seriesId)
        followingSeriesDao.insertFollowedSeries(seriesFollowedEntity)
    }

    override suspend fun unFollowSeries(seriesId: String) =
        followingSeriesDao.deleteFollowedSeries(seriesId)

    override suspend fun getSeriesListByCategory(categoryId: String): List<SeriesEntity> {
        val categoryType: CategoryType = CategoryType.find(categoryId) ?: return emptyList()

        return seriesDao.run {
            when (categoryType) {
                CategoryType.RECENT -> getRecentSeries(limit = 8)
                CategoryType.POPULAR -> getPopularSeries(limit = 8)
                CategoryType.FICTION -> getFictionSeries(limit = 16)
                CategoryType.TRAVEL -> getTravelSeries(limit = 16)
            }
        }
    }

    override suspend fun searchBySeriesName(query: String) = seriesDao.getSeriesByQuery(query)

    override suspend fun getSeries(seriesId: String) = seriesDao.getSeries(seriesId)

    override suspend fun insertSeriesList(list: List<SeriesEntity>) =
        seriesDao.insertSeriesList(list)

}

