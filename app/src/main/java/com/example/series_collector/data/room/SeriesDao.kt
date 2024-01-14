package com.example.series_collector.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.series_collector.data.room.entity.SeriesEntity
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface SeriesDao {

    @Query("SELECT * FROM Series WHERE id = :seriesId")
    fun getSeries(seriesId: String): SeriesEntity

    @Query("SELECT * FROM Series WHERE name LIKE '%' || :query || '%'")
    suspend fun getSeriesByQuery(query: String): List<SeriesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeries(series: SeriesEntity)

    @Query("SELECT last_update_date FROM Series ORDER BY last_update_date DESC LIMIT 1")
    suspend fun getLastUpdateDate(): Calendar

    @Query("SELECT (SELECT COUNT(*) FROM Series) == 0")
    suspend fun isEmpty(): Boolean

    @Query("SELECT * FROM Series ORDER BY last_update_date DESC LIMIT :limit")
    suspend fun getRecentSeries(limit: Int): List<SeriesEntity>

    @Query("SELECT * FROM Series ORDER BY have_count DESC LIMIT :limit")
    suspend fun getPopularSeries(limit: Int): List<SeriesEntity>

    @Query("SELECT * FROM Series WHERE genre == 1 LIMIT :limit")
    suspend fun getFictionSeries(limit: Int): List<SeriesEntity>

    @Query("SELECT * FROM Series  WHERE genre == 2 LIMIT :limit")
    suspend fun getTravelSeries(limit: Int): List<SeriesEntity>

    @Query("SELECT thumbnail FROM Series ORDER BY RANDOM() LIMIT :limit")
    suspend fun getRandomThumbnails(limit: Int): List<String>

}