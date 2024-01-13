package com.example.series_collector.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.series_collector.data.model.GenreType
import com.example.series_collector.data.model.Series
import com.example.series_collector.data.repository.SeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    seriesRepository: SeriesRepository
) : ViewModel() {

    private val _filterType: MutableStateFlow<SearchFilterType> =
        MutableStateFlow(SearchFilterType.ALL_FILTER_PAGE)

    private val _searchQuery: MutableStateFlow<String> = MutableStateFlow("")

    private val _searchedResult = _searchQuery
        .debounce(300)
        .mapLatest { searchQuery ->
            seriesRepository.searchBySeriesName(searchQuery)
        }

    val filteredSeries =
        combine(_searchedResult, _filterType) { result, filterType ->
            filterSeries(result, filterType)
        }.asLiveData()

    fun search(query: String) {
        _searchQuery.value = query
    }

    fun setFiltering(type: SearchFilterType) {
        _filterType.value = type
    }

    private fun filterSeries(
        contents: List<Series>,
        filteringType: SearchFilterType
    ): List<Series> {
        return when (filteringType) {
            SearchFilterType.ALL_FILTER_PAGE -> contents.toList()
            SearchFilterType.FICTION_FILTER_PAGE -> filterByGenre(contents, GenreType.FICTION)
            SearchFilterType.TRAVEL_FILTER_PAGE -> filterByGenre(contents, GenreType.TRAVEL)
        }
    }

    private fun filterByGenre(contents: List<Series>, genre: GenreType): List<Series> {
        return contents.filter { it.genreType == genre }
    }
}

