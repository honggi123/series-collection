package com.example.series_collector.ui.detail


import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.series_collector.data.api.ApiResult
import com.example.series_collector.data.model.Series
import com.example.series_collector.data.model.dto.SeriesVideo
import com.example.series_collector.data.model.SeriesWithPageInfo
import com.example.series_collector.data.model.Tag
import com.example.series_collector.data.model.TagType
import com.example.series_collector.data.repository.SeriesFollowedRepository
import com.example.series_collector.data.repository.SeriesRepository
import com.example.series_collector.utils.getGenreName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val seriesFollowedRepository: SeriesFollowedRepository,
    private val seriesRepository: SeriesRepository,
) : ViewModel() {

    val seriesId: String = savedStateHandle.get<String>(SERIES_ID_SAVED_STATE_KEY)!!

    val isFollowed = seriesFollowedRepository.isFollowed(seriesId).asLiveData()

    private val seriesInfoFlow =
        seriesRepository.getSeriesWithPageInfoStream(seriesId = seriesId, limit = 1)

    private var _tags = MutableLiveData<List<Tag>>()
    val tags: LiveData<List<Tag>> = _tags

    private var _seriesInfo = MutableLiveData<SeriesWithPageInfo?>()
    val seriesInfo: LiveData<SeriesWithPageInfo?> = _seriesInfo

    private var _errorMsg = MutableLiveData<String?>()
    val errorMsg: LiveData<String?> = _errorMsg

    init {
        observeSeriesInfoFlow()
    }

    private fun observeSeriesInfoFlow() {
        viewModelScope.launch {
            seriesInfoFlow.collect { result ->
                when (result) {
                    is ApiResult.Success -> {
                        val tags = getTagsBySeriesInfo(result.value)
                        _tags.postValue(tags)
                        _seriesInfo.postValue(result.value)
                    }

                    is ApiResult.Failure -> {
                        _errorMsg.postValue(result.msg)
                    }

                    is ApiResult.NetworkError -> {
                        _errorMsg.postValue(result.exception?.message)
                    }
                }
            }
        }
    }

    private fun getTagsBySeriesInfo(info: SeriesWithPageInfo): List<Tag> {
        return listOf(
            Tag(TagType.GENRE, getGenreName(info.series.genre)),
            Tag(TagType.CHANNEL, info.series.channel),
            Tag(TagType.TOTAL_PAGE, info.totalPage.toString())
        )
    }

    fun toggleSeriesFollowed(isFollowed: Boolean) {
        viewModelScope.launch {
            seriesFollowedRepository.run {
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



