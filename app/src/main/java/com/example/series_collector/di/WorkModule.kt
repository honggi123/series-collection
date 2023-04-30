package com.example.series_collector.di

import com.example.series_collector.utils.workers.SeriesWork
import com.example.series_collector.utils.workers.SeriesWorkImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class WorkModule {
    @Provides
    fun provideSeriesWork(seriesWorkImpl: SeriesWorkImpl): SeriesWork = seriesWorkImpl
}
