package com.example.series_collector.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.series_collector.data.model.GenreType
import com.example.series_collector.data.model.Series
import com.example.series_collector.data.repository.SeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    seriesRepository: SeriesRepository
) : ViewModel() {

    private val _selectedFilter = MutableStateFlow(SearchFilterType.ALL)
    private val _searchQuery = MutableStateFlow("")

    private val _searchedResult = _searchQuery
        .debounce(300)
        .mapLatest { query ->
            seriesRepository.searchBySeriesName(query)
        }

    val filteredSeries =
        combine(_searchedResult, _selectedFilter) { result, filter ->
            filterSeries(result, filter)
        }.asLiveData()

    fun search(query: String) {
        _searchQuery.value = query
    }

    fun setFiltering(type: SearchFilterType) {
        _selectedFilter.value = type
    }

    private fun filterSeries(
        contents: List<Series>,
        filteringType: SearchFilterType
    ): List<Series> {
        return when (filteringType) {
            SearchFilterType.ALL -> contents.toList()
            SearchFilterType.FICTION -> filterByGenre(contents, GenreType.FICTION)
            SearchFilterType.TRAVEL -> filterByGenre(contents, GenreType.TRAVEL)
        }
    }

    private fun filterByGenre(contents: List<Series>, genre: GenreType): List<Series> {
        return contents.filter { it.genreType == genre }
    }
}

