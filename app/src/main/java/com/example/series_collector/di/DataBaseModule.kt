package com.example.series_collector.di

import android.content.Context
import com.example.series_collector.data.room.AppDatabase
import com.example.series_collector.data.room.FollowedSeriesDao
import com.example.series_collector.data.room.SeriesDao

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideSeriesDao(appDatabase: AppDatabase): SeriesDao {
        return appDatabase.seriesDao()
    }

    @Provides
    fun provideFollowedSeriesDao(appDatabase: AppDatabase): FollowedSeriesDao {
        return appDatabase.followedSeriesDao()
    }
}

