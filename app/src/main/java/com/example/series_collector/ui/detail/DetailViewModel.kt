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
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    private val seriesRepository: SeriesRepository,
    private val episodeRepository: EpisodeRepository,
    private val userRepository: UserRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val seriesId: String = savedStateHandle.get<String>(SERIES_ID_SAVED_STATE_KEY)!!

    private var _errorMsg = MutableLiveData<String?>()
    val errorMsg: LiveData<String?> = _errorMsg

    val series: LiveData<Series?> = seriesRepository.getSeries(seriesId)
        .catch {
            _errorMsg.value = "시리즈 정보를 가져오지 못했습니다."
        }
        .asLiveData()

    val tags: LiveData<List<Tag>> = seriesRepository.getTagList(seriesId)
        .catch {
            _errorMsg.value = "태그 정보를 가져오지 못했습니다."
        }
        .asLiveData()

    val isFollowed = userRepository.isFollowed(seriesId)
        .catch {
            // TODO add log
        }
        .asLiveData()

    val episodes = episodeRepository.getEpisodeList(seriesId)
        .cachedIn(viewModelScope)

    fun toggleSeriesFollow(isFollowed: Boolean) {
        viewModelScope.launch {
            userRepository.run {
                if (isFollowed) setSeriesUnFollowed(seriesId)
                else setSeriesFollowed(seriesId)
            }
        }
    }

    companion object {
        private const val SERIES_ID_SAVED_STATE_KEY = "seriesId"
    }
}

