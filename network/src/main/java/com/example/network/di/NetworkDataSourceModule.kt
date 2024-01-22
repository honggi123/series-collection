package com.example.network.di

import com.example.data.source.network.CategoryRemoteDataSource
import com.example.data.source.network.EpisodeRemoteDataSource
import com.example.data.source.network.SeriesRemoteDataSource
import com.example.network.impl.CategoryRemoteDataSourceImpl
import com.example.network.impl.EpisodeRemoteDataSourceImpl
import com.example.network.impl.SeriesRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class NetworkDataSourceModule {

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