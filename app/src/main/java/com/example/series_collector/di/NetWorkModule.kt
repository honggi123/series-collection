package com.example.series_collector.di

import com.example.series_collector.data.api.YouTubeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetWorkModule {

    @Singleton
    @Provides
    fun provideUnsplashService(): YouTubeService {
        return YouTubeService.create()
    }

}