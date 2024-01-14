package com.example.series_collector.di

import com.example.series_collector.BuildConfig
import com.example.series_collector.data.api.adpater.ApiCallAdapterFactory
import com.example.series_collector.data.api.service.YoutubeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetWorkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .apply {
                if (BuildConfig.DEBUG) {
                    val loggingInterceptor =
                        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

                    addInterceptor(loggingInterceptor)
                }
            }
            .build()
    }

    @Singleton
    @Provides
    fun provideUnsplashService(client: OkHttpClient): YoutubeService {
        val BASE_URL = "https://www.googleapis.com/youtube/v3/"

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ApiCallAdapterFactory.create())
            .build()
            .create(YoutubeService::class.java)
    }

}