package com.example.series_collector.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.series_collector.data.model.dto.SeriesVideo
import com.example.series_collector.data.api.YoutubeService

private const val STARTING_PAGE_TOKEN = ""

class PlaylistPagingSource(
    private val service: YoutubeService,
    private val playlistId: String
) : PagingSource<String, SeriesVideo>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, SeriesVideo> {
        val page = params.key ?: STARTING_PAGE_TOKEN
        return try {
            val response = service.getYoutubePlayListItems(id = playlistId, pageToken = page, maxResults = 5)
            val items = response.body()!!.items
            LoadResult.Page(
                data = items,
                prevKey = if (page == STARTING_PAGE_TOKEN) null else response.body()!!.prevPageToken,
                nextKey = if (items.isEmpty()) null
                else response.body()!!.nextPageToken
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<String, SeriesVideo>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }


}
