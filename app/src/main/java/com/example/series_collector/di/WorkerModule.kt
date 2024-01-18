package com.example.series_collector.di

import com.example.series_collector.workers.SeriesWorkerManager
import com.example.series_collector.workers.SeriesWorkerManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class WorkerModule {
    @Binds
    abstract fun bindSeriesWorkerManager(impl: SeriesWorkerManagerImpl): SeriesWorkerManager
}
