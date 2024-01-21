package com.example.series_collector.local.impl

import com.example.series_collector.data.source.local.SeriesLocalDataSource
import com.example.series_collector.model.category.CategoryType
import com.example.series_collector.model.series.Series
import com.example.series_collector.model.series.toSeriesEntity
import com.example.series_collector.local.room.FollowedSeriesDao
import com.example.series_collector.local.room.SeriesDao
import com.example.series_collector.local.room.entity.FollowedSeriesEntity
import com.example.series_collector.local.room.entity.SeriesEntity
import com.example.series_collector.local.room.entity.toSeries
import com.example.series_collector.local.room.entity.toSeriesList
import com.example.series_collector.util.SeriesThumbnailFetcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SeriesLocalDataSourceImpl @Inject constructor(
    private val seriesDao: SeriesDao,
    private val followedSeriesDao: FollowedSeriesDao,
    private val seriesThumbnailFetcher: SeriesThumbnailFetcher
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
            seriesThumbnailFetcher.invoke(list.toSeriesList())
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