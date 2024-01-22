package com.example.local.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataBaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): com.example.local.room.AppDatabase {
        return com.example.local.room.AppDatabase.getInstance(context)
    }

    @Provides
    fun provideSeriesDao(appDatabase: com.example.local.room.AppDatabase): com.example.local.room.SeriesDao {
        return appDatabase.seriesDao()
    }

    @Provides
    fun provideFollowedSeriesDao(appDatabase: com.example.local.room.AppDatabase): com.example.local.room.FollowedSeriesDao {
        return appDatabase.followedSeriesDao()
    }
}

