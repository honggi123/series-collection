package com.example.series_collector.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.series_collector.local.room.entity.FollowedSeriesEntity
import com.example.series_collector.local.room.entity.SeriesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FollowedSeriesDao {

    @Transaction
    @Query("SELECT * FROM series WHERE id IN (SELECT DISTINCT(series_id) FROM series_followed)")
    fun getFollowedSeriesList(): Flow<List<SeriesEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM series_followed WHERE series_id = :seriesId LIMIT 1)")
    fun isFollowed(seriesId: String): Flow<Boolean>

    @Insert
    suspend fun insertFollowedSeries(series: FollowedSeriesEntity)

    @Query("DELETE FROM series_followed WHERE series_id = :seriesId")
    suspend fun deleteFollowedSeries(seriesId: String)

}

