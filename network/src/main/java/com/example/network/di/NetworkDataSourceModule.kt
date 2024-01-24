package com.example.network.di

import com.example.network.source.CategoryNetworkDataSource
import com.example.network.source.EpisodeNetworkDataSource
import com.example.network.source.SeriesNetworkDataSource
import com.example.network.source.impl.CategoryNetworkDataSourceImpl
import com.example.network.source.impl.EpisodeNetworkDataSourceImpl
import com.example.network.source.impl.SeriesNetworkDataSourceImpl
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
    abstract fun bindCategoryRemoteDataSource(source: CategoryNetworkDataSourceImpl): CategoryNetworkDataSource

    @Binds
    @Singleton
    abstract fun bindEpisodeRemoteDataSource(source: EpisodeNetworkDataSourceImpl): EpisodeNetworkDataSource

    @Binds
    @Singleton
    abstract fun bindSeriesRemoteDataSource(source: SeriesNetworkDataSourceImpl): SeriesNetworkDataSource
}