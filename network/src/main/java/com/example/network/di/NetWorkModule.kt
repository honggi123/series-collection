package com.example.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
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
//            .apply {
//                if (BuildConfig.DEBUG) {
//                    val loggingInterceptor =
//                        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
//
//                    addInterceptor(loggingInterceptor)
//                }
//            }
            .build()
    }

    @Singleton
    @Provides
    fun provideYoutubeService(client: OkHttpClient): com.example.network.api.service.YoutubeService {
        val BASE_URL = "https://www.googleapis.com/youtube/v3/"

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(com.example.network.api.adpater.ApiCallAdapterFactory.create())
            .build()
            .create(com.example.network.api.service.YoutubeService::class.java)
    }

}