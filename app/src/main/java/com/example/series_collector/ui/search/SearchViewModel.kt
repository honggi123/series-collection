package com.example.series_collector.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.data.repository.SeriesRepository
import com.example.model.series.GenreType
import com.example.model.series.Series
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    seriesRepository: SeriesRepository
) : ViewModel() {

    private val selectedFilter = MutableStateFlow(SearchFilterType.ALL)
    private val searchQuery = MutableStateFlow("")

    private val _errorMsg = MutableLiveData<String>()
    val errorMsg: LiveData<String> = _errorMsg

    val filteredSeries = searchQuery
        .debounce(300)
        .flatMapLatest { query -> seriesRepository.searchBySeriesName(query) }
        .combine(selectedFilter) { result, filter -> filterSeries(result, filter) }
        .catch { _errorMsg.value = "검색어를 가져오는데 실패했습니다." }
        .asLiveData()

    fun search(query: String) {
        searchQuery.value = query
    }

    fun setFiltering(filter: SearchFilterType) {
        selectedFilter.value = filter
    }

    private fun filterSeries(
        contents: List<Series>,
        filter: SearchFilterType,
    ): List<Series> {
        return when (filter) {
            SearchFilterType.ALL -> contents.toList()
            SearchFilterType.FICTION -> contents.filterByGenre(GenreType.FICTION)
            SearchFilterType.TRAVEL -> contents.filterByGenre(GenreType.TRAVEL)
        }
    }

    private fun List<Series>.filterByGenre(genre: GenreType): List<Series> {
        return this.filter { it.genreType == genre }
    }
}



