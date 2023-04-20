package com.example.series_collector.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.series_collector.data.Series
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface SeriesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSeries(series: List<Series?>)

    @Query("SELECT * FROM Series ORDER BY last_update_date DESC LIMIT 1")
    suspend fun getLastUpdatedSeries(): Series?

    @Query("SELECT (SELECT COUNT(*) FROM Series) == 0")
    suspend fun isEmpty(): Boolean
}