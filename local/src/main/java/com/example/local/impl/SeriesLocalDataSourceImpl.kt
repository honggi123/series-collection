package com.example.local.impl

import com.example.data.source.local.SeriesLocalDataSource
import com.example.local.room.FollowedSeriesDao
import com.example.local.room.SeriesDao
import com.example.local.room.entity.FollowedSeriesEntity
import com.example.local.room.entity.SeriesEntity
import com.example.local.room.entity.toSeries
import com.example.local.room.entity.toSeriesList
import com.example.model.category.CategoryType
import com.example.model.series.Series
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Calendar
import javax.inject.Inject

class SeriesLocalDataSourceImpl @Inject constructor(
    private val seriesDao: SeriesDao,
    private val followedSeriesDao: FollowedSeriesDao
) : SeriesLocalDataSource {

    override fun isFollowed(seriesId: String) = followedSeriesDao.isFollowed(seriesId)

    override fun isEmpty() = seriesDao.isEmpty()

    override fun getFollowingSeriesList(): Flow<List<Series>> =
        followedSeriesDao.getFollowedSeriesList().map { it.toSeriesList() }

    override suspend fun followSeries(seriesId: String) {
        val seriesFollowedEntity = FollowedSeriesEntity(seriesId)
        followedSeriesDao.insertFollowedSeries(seriesFollowedEntity)
    }

    override suspend fun unFollowSeries(seriesId: String) =
        followedSeriesDao.deleteFollowedSeries(seriesId)


    override suspend fun getSeriesListByCategory(categoryId: String): List<Series> {
        val categoryType: CategoryType = CategoryType.find(categoryId) ?: return emptyList()

        return seriesDao.run {
            val list =
                when (categoryType) {
                    CategoryType.RECENT -> getRecentSeries(limit = 8)
                    CategoryType.POPULAR -> getPopularSeries(limit = 8)
                    CategoryType.FICTION -> getFictionSeries(limit = 16)
                    CategoryType.TRAVEL -> getTravelSeries(limit = 16)
                }
            list.toSeriesList()
        }
    }

    override suspend fun searchBySeriesName(query: String) =
        seriesDao.getSeriesByQuery(query).toSeriesList()


    override suspend fun getSeries(seriesId: String) =
        seriesDao.getSeries(seriesId).toSeries()


    override suspend fun insertSeries(series: Series)  {
        val seriesEntity = series.toSeriesEntity()
        seriesDao.insertSeries(seriesEntity)
    }
}

fun Series.toSeriesEntity(): SeriesEntity {
    return SeriesEntity(
        id = this.id,
        name = this.name,
        description = this.description,
        channel = this.channel,
        genreIndex = this.genreIndex,
        thumbnail = this.thumbnailUrl ?: "",
        createdAt = this.createdAt ?: Calendar.getInstance(),
        updatedAt = this.updatedAt ?: Calendar.getInstance()
    )
}