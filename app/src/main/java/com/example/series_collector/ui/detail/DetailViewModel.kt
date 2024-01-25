package com.example.series_collector.ui.detail


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
import com.example.data.repository.UserRepository
import com.example.model.episode.Episode
import com.example.model.episode.PageInfo
import com.example.model.series.Series
import com.example.model.series.Tag
import com.example.model.series.TagType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val seriesRepository: SeriesRepository,
    private val episodeRepository: EpisodeRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    val seriesId: String = savedStateHandle.get<String>(SERIES_ID_SAVED_STATE_KEY)!!

    val isFollowed = userRepository.isFollowed(seriesId).asLiveData()

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
        return episodeRepository.getPageInfo(seriesId)
    }

    private fun getTagsBySeriesInfo(
        series: Series,
        pageInfo: PageInfo?
    ): List<Tag> {
        return listOf(
            Tag(
                TagType.GENRE,
                series?.genreType?.displayName
            ),
            Tag(
                TagType.CHANNEL,
                series?.channel
            ),
            Tag(
                TagType.TOTAL_PAGE,
                pageInfo?.totalResults.toString()
            )
        )
    }

    fun toggleSeriesFollow(isFollowed: Boolean) {
        viewModelScope.launch {
            userRepository.run {
                if (isFollowed) setSeriesUnFollowed(seriesId)
                else setSeriesFollowed(seriesId)
            }
        }
    }

    fun searchEpisodeList(seriesId: String): Flow<PagingData<Episode>> {
        return episodeRepository.getEpisodeListStream(seriesId = seriesId)
            .cachedIn(viewModelScope)
    }

    companion object {
        private const val SERIES_ID_SAVED_STATE_KEY = "seriesId"
    }

}



