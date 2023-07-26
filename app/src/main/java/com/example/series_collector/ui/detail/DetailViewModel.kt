package com.example.series_collector.ui.detail


import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.series_collector.data.api.ApiResult
import com.example.series_collector.data.api.SeriesVideo
import com.example.series_collector.data.model.SeriesWithPageInfo
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

    private var _tags = MutableLiveData<List<String>>()
    val tags: LiveData<List<String>> = _tags

    private var _seriesInfo = MutableLiveData<SeriesWithPageInfo?>()
    val seriesInfo: LiveData<SeriesWithPageInfo?> = _seriesInfo

    private var _errorMsg = MutableLiveData<String?>()
    val errorMsg: LiveData<String?> = _errorMsg

    private var currentQueryValue: String? = null
    private var currentSearchResult: Flow<PagingData<SeriesVideo>>? = null

    init {
        observeSeriesInfoFlow()
    }

    private fun observeSeriesInfoFlow() {
        viewModelScope.launch {
            seriesInfoFlow.collect { result ->
                when (result) {
                    is ApiResult.Success -> {
                        _tags.postValue(getTagsBySeriesInfo(result.value))
                        _seriesInfo.postValue(result.value)
                    }
                    is ApiResult.Failure ->
                        _errorMsg.postValue(result.msg)
                    is ApiResult.NetworkError ->
                        _errorMsg.postValue(result.exception?.message)
                }
            }
        }
    }

    private fun getTagsBySeriesInfo(seriesInfo: SeriesWithPageInfo) =
        listOf(
            getGenreName(seriesInfo.series.genre),
            seriesInfo.series.channel,
            seriesInfo.totalPage.toString() + "화"
            )


    fun toggleSeriesFollowed(isFollowed: Boolean) {
        viewModelScope.launch {
            seriesFollowedRepository.run {
                if (isFollowed) unFollowSeries(seriesId)
                else followSeries(seriesId)
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



