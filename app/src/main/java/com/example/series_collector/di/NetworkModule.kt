package com.example.series_collector.di

import android.content.Context
import androidx.room.Room
import com.example.series_collector.data.room.AppDatabase
import com.example.series_collector.data.room.SeriesDao
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideFireStoreService(@ApplicationContext context: Context): FirebaseFirestore {
        return Firebase.firestore
    }

}