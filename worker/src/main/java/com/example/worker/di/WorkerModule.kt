package com.example.worker.di

import com.example.worker.SeriesWorkerManager
import com.example.worker.SeriesWorkerManagerImpl
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
