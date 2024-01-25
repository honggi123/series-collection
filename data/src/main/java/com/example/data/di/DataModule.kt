package com.example.data.di

import com.example.data.repository.CategoryRepository
import com.example.data.repository.EpisodeRepository
import com.example.data.repository.SeriesRepository
import com.example.data.repository.UserRepository
import com.example.data.repository.impl.CategoryRepositoryImpl
import com.example.data.repository.impl.EpisodeRepositoryImpl
import com.example.data.repository.impl.SeriesRepositoryImpl
import com.example.data.repository.impl.UserRepositoryImpl
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
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

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