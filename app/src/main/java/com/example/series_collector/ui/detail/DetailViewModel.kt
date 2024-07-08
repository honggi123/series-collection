package com.example.series_collector.ui.detail


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.data.repository.EpisodeRepository
import com.example.data.repository.SeriesRepository
import com.example.data.repository.UserRepository
import com.example.model.series.Series
import com.example.model.series.Tag
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class DetailViewModel @Inject constructor(
    private val seriesRepository: SeriesRepository,
    private val episodeRepository: EpisodeRepository,
    private val userRepository: UserRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val seriesId: String = savedStateHandle.get<String>(SERIES_ID_SAVED_STATE_KEY)!!

    private val _tags = MutableLiveData<List<Tag>>(listOf())
    val tags: LiveData<List<Tag>> = _tags

    val series: LiveData<Series?> = seriesRepository.getSeries(seriesId)
        .onEach { _tags.value = getTaggedSeries(it) }
        .asLiveData()

    val isFollowed = userRepository.isFollowed(seriesId)
        .asLiveData()

    val episodes = episodeRepository.getEpisodeList(seriesId)
        .cachedIn(viewModelScope)

    private var _errorMsg = MutableLiveData<String?>()
    val errorMsg: LiveData<String?> = _errorMsg

    fun toggleSeriesFollow(isFollowed: Boolean) {
        viewModelScope.launch {
            userRepository.run {
                if (isFollowed) setSeriesUnFollowed(seriesId)
                else setSeriesFollowed(seriesId)
            }
        }
    }

    private fun getTaggedSeries(series: Series): List<Tag> {
        TODO()
    }

    companion object {
        private const val SERIES_ID_SAVED_STATE_KEY = "seriesId"
    }
}

