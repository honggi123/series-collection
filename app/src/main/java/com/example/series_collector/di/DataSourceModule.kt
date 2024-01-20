package com.example.series_collector.di

import com.example.series_collector.data.source.local.SeriesLocalDataSource
import com.example.series_collector.data.source.remote.CategoryRemoteDataSource
import com.example.series_collector.data.source.remote.EpisodeRemoteDataSource
import com.example.series_collector.data.source.remote.SeriesRemoteDataSource
import com.example.series_collector.local.impl.SeriesLocalDataSourceImpl
import com.example.series_collector.remote.impl.CategoryRemoteDataSourceImpl
import com.example.series_collector.remote.impl.EpisodeRemoteDataSourceImpl
import com.example.series_collector.remote.impl.SeriesRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindSeriesLocalDataSource(source: SeriesLocalDataSourceImpl): SeriesLocalDataSource

    @Binds
    @Singleton
    abstract fun bindCategoryRemoteDataSource(source: CategoryRemoteDataSourceImpl): CategoryRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindEpisodeRemoteDataSource(source: EpisodeRemoteDataSourceImpl): EpisodeRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindSeriesRemoteDataSource(source: SeriesRemoteDataSourceImpl): SeriesRemoteDataSource
}