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

    private fun filterSeries(tasks: List<Series>, filteringType: SearchFilterType): List<Series> {
        val seriesToShow = ArrayList<Series>()
        for (task in tasks) {
            when (filteringType) {
                SearchFilterType.ALL_FILTER_PAGE -> seriesToShow.add(task)
                SearchFilterType.FICTION_FILTER_PAGE -> if (task.genre == 1) {
                    seriesToShow.add(task)
                }
                SearchFilterType.TRAVEL_FILTER_PAGE -> if (task.genre == 2) {
                    seriesToShow.add(task)
                }
            }
        }
        return seriesToShow
    }

}