package com.example.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.local.entity.FollowingSeriesEntity
import com.example.local.entity.SeriesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FollowingSeriesDao {

    @Transaction
    @Query("SELECT * FROM series WHERE id IN (SELECT DISTINCT(series_id) FROM series_followed)")
    fun getFollowingSeriesList(): Flow<List<SeriesEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM series_followed WHERE series_id = :seriesId LIMIT 1)")
    fun isFollowed(seriesId: String): Flow<Boolean>

    @Insert
    suspend fun insertSeriesFollowed(series: FollowingSeriesEntity)

    @Query("DELETE FROM series_followed WHERE series_id = :seriesId")
    suspend fun deleteSeriesFollowed(seriesId: String)

}

