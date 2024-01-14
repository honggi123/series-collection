package com.example.series_collector.data.source.youtube

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.series_collector.data.api.ApiResultError
import com.example.series_collector.data.api.ApiResultException
import com.example.series_collector.data.api.ApiResultSuccess
import com.example.series_collector.data.api.YoutubeService
import com.example.series_collector.data.api.model.SeriesVideo

private const val STARTING_PAGE_TOKEN = ""
class PlaylistPagingSource(
    private val service: YoutubeService,
    private val playlistId: String
) : PagingSource<String, SeriesVideo>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, SeriesVideo> {
        val page = params.key ?: STARTING_PAGE_TOKEN
        val response = service.getYoutubePlayListItems(id = playlistId, pageToken = page, maxResults = 5)
        return when (response) {
            is ApiResultSuccess -> {
                val items = response.data.items
                LoadResult.Page(
                    data = items,
                    prevKey = if (page == STARTING_PAGE_TOKEN) null else response.data.prevPageToken,
                    nextKey = if (items.isEmpty()) null
                    else response.data.nextPageToken
                )
            }
            is ApiResultException -> LoadResult.Error(response.throwable)
            is ApiResultError -> LoadResult.Error(Exception(response.message))
        }
    }

    override fun getRefreshKey(state: PagingState<String, SeriesVideo>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }
}