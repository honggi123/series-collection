package com.example.series_collector.ui.detail


import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.series_collector.remote.api.adpater.ApiResultError
import com.example.series_collector.remote.api.adpater.ApiResultException
import com.example.series_collector.remote.api.adpater.ApiResultSuccess
import com.example.series_collector.remote.api.model.PageInfo
import com.example.series_collector.remote.api.model.SeriesEpisode
import com.example.series_collector.data.model.Series
import com.example.series_collector.data.model.Tag
import com.example.series_collector.data.model.TagType
import com.example.series_collector.data.repository.EpisodeRepository
import com.example.series_collector.data.repository.SeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val seriesRepository: SeriesRepository,
    private val episodeRepository: EpisodeRepository
) : ViewModel() {

    val seriesId: String = savedStateHandle.get<String>(SERIES_ID_SAVED_STATE_KEY)!!

    val isFollowed = seriesRepository.isFollowed(seriesId).asLiveData()

    private var _tags = MutableLiveData<List<Tag>>()
    val tags: LiveData<List<Tag>> = _tags

    private var _series = MutableLiveData<Series?>()
    val series: LiveData<Series?> = _series

    private var _errorMsg = MutableLiveData<String?>()
    val errorMsg: LiveData<String?> = _errorMsg

    init {
        initAll()
    }

    private fun initAll() {
        viewModelScope.launch {
            val series = seriesRepository.getSeries(seriesId)
            val pageInfo = getPageInfo()
            val tags = getTagsBySeriesInfo(series, pageInfo)

            _series.value = series
            _tags.value = tags
        }
    }

    private suspend fun getPageInfo(): PageInfo? {
        val result = episodeRepository.getEpisodeList(seriesId)

        return when (result) {
            is ApiResultSuccess -> result.data.pageInfo
            is ApiResultError -> TODO()
            is ApiResultException -> TODO()
        }
    }

    private fun getTagsBySeriesInfo(series: Series, pageInfo: PageInfo?): List<Tag> {
        return listOf(
            Tag(TagType.GENRE, series?.genreType?.displayName),
            Tag(TagType.CHANNEL, series?.channel),
            Tag(TagType.TOTAL_PAGE, pageInfo?.totalResults.toString())
        )
    }

    fun toggleSeriesFollow(isFollowed: Boolean) {
        viewModelScope.launch {
            seriesRepository.run {
                if (isFollowed) unFollowSeries(seriesId)
                else followSeries(seriesId)
            }
        }
    }

    fun searchEpisodeList(seriesId: String): Flow<PagingData<SeriesEpisode>> {
        return episodeRepository.getEpisodeListStream(seriesId = seriesId)
            .cachedIn(viewModelScope)
    }

    companion object {
        private const val SERIES_ID_SAVED_STATE_KEY = "seriesId"
    }

}



