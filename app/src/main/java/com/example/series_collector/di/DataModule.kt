package com.example.series_collector.di

import com.example.series_collector.data.repository.CategoryRepository
import com.example.series_collector.data.repository.EpisodeRepository
import com.example.series_collector.data.repository.SeriesRepository
import com.example.series_collector.data.repository.impl.CategoryRepositoryImpl
import com.example.series_collector.data.repository.impl.EpisodeRepositoryImpl
import com.example.series_collector.data.repository.impl.SeriesRepositoryImpl
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
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindSeriesRepository(impl: SeriesRepositoryImpl): SeriesRepository

    @Binds
    @Singleton
    abstract fun bindCategoryRepository(impl: CategoryRepositoryImpl): CategoryRepository

    @Binds
    @Singleton
    abstract fun bindEpisodeRepository(impl: EpisodeRepositoryImpl): EpisodeRepository

}