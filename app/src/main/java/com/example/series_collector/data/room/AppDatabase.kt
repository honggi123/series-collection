package com.example.series_collector.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.series_collector.data.room.entity.SeriesFollowedEntity
import com.example.series_collector.data.room.entity.SeriesEntity

@Database(entities = [SeriesEntity::class, SeriesFollowedEntity::class], version = 3, exportSchema = false)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun seriesDao(): SeriesDao
    abstract fun seriesFollwedDao(): SeriesFollowedDao

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "sereis_db")
                .build()
        }
    }
}