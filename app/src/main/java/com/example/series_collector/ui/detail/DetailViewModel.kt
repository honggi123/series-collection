package com.example.series_collector.ui.detail


import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.series_collector.data.api.ApiResult
import com.example.series_collector.data.api.SeriesVideo
import com.example.series_collector.data.model.SeriesWithPageInfo
import com.example.series_collector.data.repository.SeriesFollowedRepository
import com.example.series_collector.data.repository.SeriesRepository
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

    private var _seriesInfo = MutableLiveData<SeriesWithPageInfo?>()
    val seriesInfo: LiveData<SeriesWithPageInfo?> = _seriesInfo

    private var _errorMsg = MutableLiveData<String?>()
    val errorMsg: LiveData<String?> = _errorMsg

    private var currentQueryValue: String? = null
    private var currentSearchResult: Flow<PagingData<SeriesVideo>>? = null

    init {
        getSeriesInfoFlow(seriesId)
    }

    private fun getSeriesInfoFlow(seriesId: String) {
        viewModelScope.launch {
            seriesRepository.getSeriesWithPageInfoStream(seriesId = seriesId, limit = 1)
                .collect { result ->
                    when (result) {
                        is ApiResult.Success ->
                            _seriesInfo.postValue(result.value)
                        is ApiResult.Empty ->
                            _errorMsg.postValue("Response data is empty")
                        is ApiResult.Error ->
                            _errorMsg.postValue(result.exception?.message)
                    }
                }
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
            seriesRepository.getPlaylistResultStream(playlistId = seriesId).cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }


    companion object {
        private const val SERIES_ID_SAVED_STATE_KEY = "seriesId"
    }

}



