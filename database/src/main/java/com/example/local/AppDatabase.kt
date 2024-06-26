package com.example.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.local.dao.FollowingSeriesDao
import com.example.local.dao.SeriesDao
import com.example.local.entity.FollowingSeriesEntity
import com.example.local.entity.SeriesEntity

@Database(entities = [SeriesEntity::class, FollowingSeriesEntity::class], version = 4, exportSchema = false)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun seriesDao(): SeriesDao
    abstract fun followedSeriesDao(): FollowingSeriesDao

    companion object {
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