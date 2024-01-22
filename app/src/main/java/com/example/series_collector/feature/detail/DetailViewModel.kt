package com.example.series_collector.feature.detail


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.data.repository.EpisodeRepository
import com.example.data.repository.SeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
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

    private var _tags = MutableLiveData<List<com.example.model.common.Tag>>()
    val tags: LiveData<List<com.example.model.common.Tag>> = _tags

    private var _series = MutableLiveData<com.example.model.series.Series?>()
    val series: LiveData<com.example.model.series.Series?> = _series

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

    private suspend fun getPageInfo(): com.example.model.episode.PageInfo? {
        return episodeRepository.getPageInfo(seriesId)
    }

    private fun getTagsBySeriesInfo(series: com.example.model.series.Series, pageInfo: com.example.model.episode.PageInfo?): List<com.example.model.common.Tag> {
        return listOf(
            com.example.model.common.Tag(
                com.example.model.common.TagType.GENRE,
                series?.genreType?.displayName
            ),
            com.example.model.common.Tag(com.example.model.common.TagType.CHANNEL, series?.channel),
            com.example.model.common.Tag(
                com.example.model.common.TagType.TOTAL_PAGE,
                pageInfo?.totalResults.toString()
            )
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

    fun searchEpisodeList(seriesId: String): Flow<PagingData<com.example.model.episode.Episode>> {
        return episodeRepository.getEpisodeListStream(seriesId = seriesId)
            .cachedIn(viewModelScope)
    }

    companion object {
        private const val SERIES_ID_SAVED_STATE_KEY = "seriesId"
    }

}



