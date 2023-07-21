package com.example.series_collector.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.series_collector.data.model.Series
import com.example.series_collector.data.repository.SeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    seriesRepository: SeriesRepository
) : ViewModel() {

    val filterTypes = SearchFilterType.values().toList()

    private val _searchQuery: MutableStateFlow<String?> = MutableStateFlow("")

    val searchResult: LiveData<List<Series>?> =
        _searchQuery.debounce(300)
            .mapLatest { query ->
                query?.let { seriesRepository.searchBySeriesName(it) }
            }.asLiveData()

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

}