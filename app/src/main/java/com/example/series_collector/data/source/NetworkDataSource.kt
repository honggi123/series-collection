package com.example.series_collector.data.source

import com.example.series_collector.data.Category
import com.example.series_collector.data.Series
import com.example.series_collector.data.api.PlayListThumbnailRespons
import com.example.series_collector.data.api.YouTubeService
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import retrofit2.Response
import java.util.*
import javax.inject.Inject

class NetworkDataSource @Inject constructor(
    private val youTubeService: YouTubeService
) {
    suspend fun getUpdatedSeries(lastUpdate: Calendar): List<Series> {
        return lastUpdate.let {
            Firebase.firestore.collection("Series")
                .whereGreaterThanOrEqualTo("updateAt", it.time)
                .get()
                .await().toObjects(Series::class.java)
        }
    }

    suspend fun getCategorys(): MutableList<Category> = Firebase.firestore.collection("Category")
        .get()
        .await().toObjects(Category::class.java)

    suspend fun getThumbNailImage(playListId: String): String =
        youTubeService.run {
            getYoutubePlayListItems(id = playListId, maxResults = 1)
                .items.get(0).snippet.thumbnails.default.url
        }
}