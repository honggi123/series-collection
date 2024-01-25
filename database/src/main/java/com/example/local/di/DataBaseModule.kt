package com.example.local.di

import android.content.Context
import com.example.local.AppDatabase
import com.example.local.dao.FollowingSeriesDao
import com.example.local.dao.SeriesDao
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
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideSeriesDao(appDatabase: AppDatabase): SeriesDao {
        return appDatabase.seriesDao()
    }

    @Provides
    fun provideFollowedSeriesDao(appDatabase: AppDatabase): FollowingSeriesDao {
        return appDatabase.followedSeriesDao()
    }
}

