package com.example.series_collector.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.data.repository.SeriesRepository
import com.example.model.series.GenreType
import com.example.model.series.Series
import com.example.data.repository.impl.SeriesRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    seriesRepository: com.example.data.repository.SeriesRepository
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
        contents: List<com.example.model.series.Series>,
        filteringType: SearchFilterType
    ): List<com.example.model.series.Series> {
        return when (filteringType) {
            SearchFilterType.ALL -> contents.toList()
            SearchFilterType.FICTION -> filterByGenre(contents, com.example.model.series.GenreType.FICTION)
            SearchFilterType.TRAVEL -> filterByGenre(contents, com.example.model.series.GenreType.TRAVEL)
        }
    }

    private fun filterByGenre(contents: List<com.example.model.series.Series>, genre: com.example.model.series.GenreType): List<com.example.model.series.Series> {
        return contents.filter { it.genreType == genre }
    }
}

