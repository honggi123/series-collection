package com.example.series_collector.data.api

import com.example.series_collector.BuildConfig
import com.example.series_collector.data.api.model.PlayListsDto
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeService {

    @GET("playlistItems")
    suspend fun getYoutubePlayListItems(
        @Query("key") apiKey: String = BuildConfig.GOOGLE_API_KEY,
        @Query("part") part: String = "id, snippet",
        @Query("playlistId") id: String,
        @Query("pageToken") pageToken: String = "",
        @Query("maxResults") maxResults: Int,
    ): ApiResponse<PlayListsDto>

}