package com.example.series_collector.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.series_collector.model.episode.Episode
import com.example.series_collector.remote.api.adpater.ApiResultError
import com.example.series_collector.remote.api.adpater.ApiResultException
import com.example.series_collector.remote.api.adpater.ApiResultSuccess
import com.example.series_collector.remote.api.service.YoutubeService
import com.example.series_collector.remote.model.toEpisode

private const val STARTING_PAGE_TOKEN = ""

class EpisodePagingSource(
    private val service: YoutubeService,
    private val playlistId: String
) : PagingSource<String, Episode>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Episode> {
        val page = params.key ?: STARTING_PAGE_TOKEN
        val response = service.getYoutubePlayListItems(id = playlistId, pageToken = page, limit = 5)
        return when (response) {
            is ApiResultSuccess -> {
                val items = response.data.items.map { it.toEpisode() }
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

    override fun getRefreshKey(state: PagingState<String, Episode>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }
}