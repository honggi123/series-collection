package com.example.series_collector.data.api

import com.example.series_collector.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface YouTubeService {

    @GET("playlistItems")
    suspend fun getYoutubePlayListItems(
        @Query("key") apiKey: String = BuildConfig.GOOGLE_API_KEY,
        @Query("part") part: String = "id, snippet",
        @Query("playlistId") id: String,
        @Query("maxResults") maxResults: Int,
    ): PlayListThumbnailRespons

    companion object {
        private const val BASE_URL = "https://www.googleapis.com/youtube/v3/"

        fun create(): YouTubeService {
            val logger =
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(YouTubeService::class.java)
        }
    }
}