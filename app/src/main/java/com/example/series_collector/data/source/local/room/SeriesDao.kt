package com.example.series_collector.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.series_collector.data.source.local.room.entity.SeriesEntity
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface SeriesDao {

    @Query("SELECT * FROM Series WHERE id = :seriesId")
    suspend fun getSeries(seriesId: String): SeriesEntity

    @Query("SELECT * FROM Series WHERE name LIKE '%' || :query || '%'")
    suspend fun getSeriesByQuery(query: String): List<SeriesEntity>

    @Query("SELECT * FROM Series ORDER BY updatedAt DESC LIMIT :limit")
    suspend fun getRecentSeries(limit: Int): List<SeriesEntity>

    @Query("SELECT * FROM Series ORDER BY have_count DESC LIMIT :limit")
    suspend fun getPopularSeries(limit: Int): List<SeriesEntity>

    @Query("SELECT * FROM Series WHERE genre == 1 LIMIT :limit")
    suspend fun getFictionSeries(limit: Int): List<SeriesEntity>

    @Query("SELECT * FROM Series  WHERE genre == 2 LIMIT :limit")
    suspend fun getTravelSeries(limit: Int): List<SeriesEntity>

    @Query("SELECT (SELECT COUNT(*) FROM Series) == 0")
    fun isEmpty(): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeries(series: SeriesEntity)

}