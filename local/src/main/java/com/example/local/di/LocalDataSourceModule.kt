package com.example.local.di

import com.example.data.source.local.SeriesLocalDataSource
import com.example.local.impl.SeriesLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal abstract class LocalDataSourceModule {
    @Binds
    @Singleton
    abstract fun bindSeriesLocalDataSource(source: SeriesLocalDataSourceImpl): SeriesLocalDataSource

}