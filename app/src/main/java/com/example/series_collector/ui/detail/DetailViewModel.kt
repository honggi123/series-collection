package com.example.series_collector.ui.detail


import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.series_collector.data.Series
import com.example.series_collector.data.api.PageInfo
import com.example.series_collector.data.api.SeriesVideo
import com.example.series_collector.data.repository.SeriesFollowedRepository
import com.example.series_collector.data.repository.SeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
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

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _series = MutableLiveData<Series>()
    val series: LiveData<Series> = _series

    private val _seriesPageInfo = MutableLiveData<PageInfo>()
    val seriesPageInfo: LiveData<PageInfo> = _seriesPageInfo

    private var currentQueryValue: String? = null
    private var currentSearchResult: Flow<PagingData<SeriesVideo>>? = null


    init {
        _isLoading.value = true
        seriesRepository.getSeriesStream(seriesId)
            .onEach {
                _series.value = it
                _isLoading.value = false
            }
            .launchIn(viewModelScope)

        viewModelScope.launch {
            _seriesPageInfo.value =
                seriesRepository.getPageInfo(seriesId)
        }
    }

    fun toggleSeriesFollowed(isFollowed: Boolean) {
        viewModelScope.launch {
            if (isFollowed) {
                seriesFollowedRepository.unFollowSeries(seriesId)
            } else {
                seriesFollowedRepository.followSeries(seriesId)
            }
        }
    }



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


