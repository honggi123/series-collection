package com.example.series_collector.local

import com.example.series_collector.data.local.SeriesLocalDataSource
import com.example.series_collector.data.model.CategoryType
import com.example.series_collector.local.room.FollowedSeriesDao
import com.example.series_collector.local.room.SeriesDao
import com.example.series_collector.local.room.entity.FollowedSeriesEntity
import com.example.series_collector.local.room.entity.SeriesEntity
import com.example.series_collector.util.SeriesThumbnailFetcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SeriesLocalDataSourceImpl @Inject constructor(
    private val seriesDao: SeriesDao,
    private val followedSeriesDao: FollowedSeriesDao,
    private val seriesThumbnailFetcher: SeriesThumbnailFetcher
) : SeriesLocalDataSource {

    override fun isFollowed(seriesId: String) = followedSeriesDao.isFollowed(seriesId)

    override fun isEmpty() = seriesDao.isEmpty()

    override fun getFollowingSeriesList(): Flow<List<SeriesEntity>> =
        followedSeriesDao.getFollowedSeriesList()

    override suspend fun followSeries(seriesId: String) {
        val seriesFollowedEntity = FollowedSeriesEntity(seriesId)
        followedSeriesDao.insertFollowedSeries(seriesFollowedEntity)
    }

    override suspend fun unFollowSeries(seriesId: String) =
        followedSeriesDao.deleteFollowedSeries(seriesId)


    override suspend fun getSeriesListByCategory(categoryId: String): List<SeriesEntity> {
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

    override suspend fun searchBySeriesName(query: String) = seriesDao.getSeriesByQuery(query)


    override suspend fun getSeries(seriesId: String) = seriesDao.getSeries(seriesId)


    override suspend fun insertSeries(seriesEntity: SeriesEntity) = seriesDao.insertSeries(seriesEntity)

}