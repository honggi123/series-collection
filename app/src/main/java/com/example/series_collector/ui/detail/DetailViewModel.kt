package com.example.series_collector.ui.detail


import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.series_collector.data.Series
import com.example.series_collector.data.api.SeriesVideo
import com.example.series_collector.data.repository.SeriesFollowedRepository
import com.example.series_collector.data.repository.SeriesRepository
import com.example.series_collector.utils.getGenreName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val SUB_DESCRIPTION_PREFIX = " # "

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val seriesFollowedRepository: SeriesFollowedRepository,
    private val seriesRepository: SeriesRepository,
) : ViewModel() {

    val seriesId: String = savedStateHandle.get<String>(SERIES_ID_SAVED_STATE_KEY)!!

    val isFollowed = seriesFollowedRepository.isFollowed(seriesId).asLiveData()

    private val _series = MutableLiveData<Series>()
    val series: LiveData<Series> = _series

    private val _subDescription = MutableLiveData<String>()
    val subDescription: LiveData<String> = _subDescription

    private var currentQueryValue: String? = null
    private var currentSearchResult: Flow<PagingData<SeriesVideo>>? = null


    init {
        viewModelScope.launch {
            seriesRepository.getSeriesStream(seriesId)
                .map {
                    _subDescription.value = getSubDescription(it)
                    _series.value = it
                }
                .launchIn(viewModelScope)
        }
    }

    fun toggleSeriesFollowed(isFollowed: Boolean) {
        viewModelScope.launch {
            if (isFollowed) {
                seriesFollowedRepository.followSeries(seriesId)
            } else {
                seriesFollowedRepository.unFollowSeries(seriesId)
            }
        }
    }

    private suspend fun getSubDescription(series: Series): String =
        SUB_DESCRIPTION_PREFIX + getGenreName(series.genre) + SUB_DESCRIPTION_PREFIX + series.channel +
                SUB_DESCRIPTION_PREFIX + seriesRepository.getPageInfo(series.seriesId).totalResults + "회차"


    fun searchSeriesVideoList(seriesId: String): Flow<PagingData<SeriesVideo>> {
        currentQueryValue = seriesId
        val newResult: Flow<PagingData<SeriesVideo>> =
            seriesRepository.getPlaylistResultStream(seriesId).cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }

    companion object {
        private const val SERIES_ID_SAVED_STATE_KEY = "seriesId"
    }

}


