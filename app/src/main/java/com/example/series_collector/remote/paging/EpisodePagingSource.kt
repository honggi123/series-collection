package com.example.series_collector.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.series_collector.remote.api.adpater.ApiResultError
import com.example.series_collector.remote.api.adpater.ApiResultException
import com.example.series_collector.remote.api.adpater.ApiResultSuccess
import com.example.series_collector.remote.model.SeriesEpisode
import com.example.series_collector.remote.api.service.YoutubeService

private const val STARTING_PAGE_TOKEN = ""

class EpisodePagingSource(
    private val service: YoutubeService,
    private val playlistId: String
) : PagingSource<String, SeriesEpisode>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, SeriesEpisode> {
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

    override fun getRefreshKey(state: PagingState<String, SeriesEpisode>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }
}