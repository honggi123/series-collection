package com.example.series_collector.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import com.example.series_collector.data.Series

@Database(entities = [Series::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun SeriesDao(): SeriesDao
}