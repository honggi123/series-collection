package com.example.series_collector.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.series_collector.data.room.entity.Series
import com.example.series_collector.data.room.entity.SeriesFollowed
import kotlinx.coroutines.flow.Flow

@Dao
interface SeriesFollowedDao {

    @Query("SELECT EXISTS(SELECT 1 FROM series_followed WHERE series_id = :seriesId LIMIT 1)")
    fun isFollowed(seriesId: String): Flow<Boolean>

    @Transaction
    @Query("SELECT * FROM series WHERE id IN (SELECT DISTINCT(series_id) FROM series_followed)")
    fun getSeriesInSeriesFollowed(): Flow<List<Series>>

    @Insert
    suspend fun insertSeriesFollowed(seriesFollowed: SeriesFollowed)

    @Query("DELETE FROM series_followed WHERE series_id = :seriesId")
    suspend fun deleteSeriesFollowed(seriesId: String)
}
