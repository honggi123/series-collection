package com.example.series_collector.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
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
        .mapLatest { query ->
            seriesRepository.searchBySeriesName(query)
        }

    val filteredSeries =
        combine(_searchedResult, _filterType) { list, type ->
            filterSeries(list, type)
        }.asLiveData()

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setFiltering(requestType: SearchFilterType) {
        _filterType.value = requestType
    }

    private fun filterSeries(seriesContents: List<Series>, filteringType: SearchFilterType): List<Series> {
        val seriesToShow = ArrayList<Series>()
        for (seriesContent in seriesContents) {
            when (filteringType) {
                SearchFilterType.ALL_FILTER_PAGE -> seriesToShow.add(seriesContent)
                SearchFilterType.FICTION_FILTER_PAGE -> if (seriesContent.genre == 1) {
                    seriesToShow.add(seriesContent)
                }
                SearchFilterType.TRAVEL_FILTER_PAGE -> if (seriesContent.genre == 2) {
                    seriesToShow.add(seriesContent)
                }
            }
        }
        return seriesToShow
    }

}