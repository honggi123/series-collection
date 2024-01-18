package com.example.series_collector.ui.detail


import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.series_collector.data.api.model.SeriesVideo
import com.example.series_collector.data.model.SeriesWithPageInfo
import com.example.series_collector.data.model.Tag
import com.example.series_collector.data.model.TagType
import com.example.series_collector.data.repository.SeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val seriesRepository: SeriesRepository,
) : ViewModel() {

    val seriesId: String = savedStateHandle.get<String>(SERIES_ID_SAVED_STATE_KEY)!!

    val isFollowed = seriesRepository.isFollowed(seriesId).asLiveData()

    private var _tags = MutableLiveData<List<Tag>>()
    val tags: LiveData<List<Tag>> = _tags

    private var _seriesInfo = MutableLiveData<SeriesWithPageInfo?>()
    val seriesInfo: LiveData<SeriesWithPageInfo?> = _seriesInfo

    private var _errorMsg = MutableLiveData<String?>()
    val errorMsg: LiveData<String?> = _errorMsg

    init {
        initAll()
    }

    private fun initAll() {
        viewModelScope.launch {
            seriesRepository.getSeriesWithPageInfo(seriesId = seriesId)
                .collect { result ->
                    val tags = getTagsBySeriesInfo(result)
                    _tags.postValue(tags)
                    _seriesInfo.postValue(result)
                }
        }
    }

    private fun getTagsBySeriesInfo(info: SeriesWithPageInfo): List<Tag> {
        return listOf(
            Tag(TagType.GENRE, info.series?.genreType?.displayName),
            Tag(TagType.CHANNEL, info.series?.channel),
            Tag(TagType.TOTAL_PAGE, info.pageInfo?.totalResults.toString())
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

    fun searchSeriesVideoList(seriesId: String): Flow<PagingData<SeriesVideo>> {
        return seriesRepository.getPlaylistResultStream(playlistId = seriesId)
            .cachedIn(viewModelScope)
    }

    companion object {
        private const val SERIES_ID_SAVED_STATE_KEY = "seriesId"
    }

}



