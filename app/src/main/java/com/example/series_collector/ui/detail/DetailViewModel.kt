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
import com.example.model.episode.PageInfo
import com.example.model.series.Series
import com.example.model.series.Tag
import com.example.model.series.TagType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class DetailViewModel @Inject constructor(
    private val seriesRepository: SeriesRepository,
    private val episodeRepository: EpisodeRepository,
    private val userRepository: UserRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val seriesId: String = savedStateHandle.get<String>(SERIES_ID_SAVED_STATE_KEY)!!

    val series: LiveData<Pair<Series?, List<Tag>>> = seriesRepository.getSeries(seriesId)
        .flatMapLatest { series -> getTaggedSeries(series) }
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

    private fun getTaggedSeries(series: Series): Flow<Pair<Series, List<Tag>>> {
        return episodeRepository.getPageInfo(series.id)
            .map { series to series.toTags(it) }
    }

    companion object {
        private const val SERIES_ID_SAVED_STATE_KEY = "seriesId"
    }
}

private fun Series.toTags(pageInfo: PageInfo?): List<Tag> {
    return listOf(
        Tag(
            TagType.GENRE,
            this.genreType?.displayName
        ),
        Tag(
            TagType.CHANNEL,
            this.channel
        ),
        Tag(
            TagType.TOTAL_PAGE,
            pageInfo?.totalResults.toString()
        )
    )
}


