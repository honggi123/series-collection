package com.example.series_collector.data.source.local

import com.example.series_collector.data.model.CategoryType
import com.example.series_collector.data.source.local.room.FollowedSeriesDao
import com.example.series_collector.data.source.local.room.SeriesDao
import com.example.series_collector.data.source.local.room.entity.FollowedSeriesEntity
import com.example.series_collector.data.source.local.room.entity.SeriesEntity
import com.example.series_collector.util.SeriesThumbnailFetcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SeriesLocalDataSource @Inject constructor(
    private val seriesDao: SeriesDao,
    private val followedSeriesDao: FollowedSeriesDao,
    private val seriesThumbnailFetcher: SeriesThumbnailFetcher
) {

    fun isFollowed(seriesId: String) = followedSeriesDao.isFollowed(seriesId)

    fun getFollowingSeriesList(): Flow<List<SeriesEntity>> =
        followedSeriesDao.getFollowedSeriesList()

    suspend fun followSeries(seriesId: String) {
        val seriesFollowedEntity = FollowedSeriesEntity(seriesId)
        followedSeriesDao.insertFollowedSeries(seriesFollowedEntity)
    }

    suspend fun unFollowSeries(seriesId: String) {
        followedSeriesDao.deleteFollowedSeries(seriesId)
    }

    fun isEmpty() = seriesDao.isEmpty()

    suspend fun getSeriesListByCategory(categoryId: String): List<SeriesEntity> {
        val categoryType: CategoryType = CategoryType.find(categoryId) ?: return emptyList()

        return seriesDao.run {
            val list =
                when (categoryType) {
                    CategoryType.RECENT -> getRecentSeries(limit = 8)
                    CategoryType.POPULAR -> getPopularSeries(limit = 8)
                    CategoryType.FICTION -> getFictionSeries(limit = 16)
                    CategoryType.TRAVEL -> getTravelSeries(limit = 16)
                }
            seriesThumbnailFetcher.invoke(list)
        }
    }

    suspend fun searchBySeriesName(query: String): List<SeriesEntity> {
        return seriesDao.getSeriesByQuery(query)
    }

    suspend fun getSeries(seriesId: String, limit: Int = 1): SeriesEntity {
        return seriesDao.getSeries(seriesId)
    }

}