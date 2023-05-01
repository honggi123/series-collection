package com.example.series_collector.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.series_collector.data.MySeries
import com.example.series_collector.data.Series
import kotlinx.coroutines.flow.Flow

@Dao
interface MySeriesDao {

    @Query("SELECT EXISTS(SELECT 1 FROM my_series WHERE series_id = :seriesId LIMIT 1)")
    fun isMySeries(seriesId: String): Flow<Boolean>

    @Insert
    suspend fun insertMySeries(mySeries: MySeries)

    @Query("DELETE FROM my_series WHERE series_id = :seriesId")
    suspend fun deleteMySeries(seriesId: String)
}
