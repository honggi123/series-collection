package com.example.series_collector.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.series_collector.data.SeriesFollowed
import kotlinx.coroutines.flow.Flow

@Dao
interface SeriesFollowedDao {

    @Query("SELECT EXISTS(SELECT 1 FROM series_followed WHERE series_id = :seriesId LIMIT 1)")
    fun isFollowed(seriesId: String): Flow<Boolean>

    @Insert
    suspend fun insertSeriesFollowed(seriesFollowed: SeriesFollowed)

    @Query("DELETE FROM series_followed WHERE series_id = :seriesId")
    suspend fun deleteSeriesFollowed(seriesId: String)
}
