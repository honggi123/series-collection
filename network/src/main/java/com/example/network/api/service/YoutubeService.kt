package com.example.network.api.service

import com.example.network.BuildConfig
import com.example.network.api.adpater.ApiResponse
import com.example.network.model.EpisodeListsDto
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeService {

    @GET("playlistItems")
    suspend fun getYoutubePlayListItems(
        @Query("key") apiKey: String = BuildConfig.GOOGLE_API_KEY,
        @Query("part") part: String = "id, snippet",
        @Query("playlistId") id: String,
        @Query("pageToken") pageToken: String = "",
        @Query("maxResults") limit: Int,
    ): ApiResponse<EpisodeListsDto>

}