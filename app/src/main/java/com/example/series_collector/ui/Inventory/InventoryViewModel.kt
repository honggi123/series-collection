package com.example.series_collector.ui.Inventory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.series_collector.data.repository.SeriesFollowedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val seriesFollowedRepository: SeriesFollowedRepository
) : ViewModel() {
    val mySeriesList = seriesFollowedRepository.getSeriesFollowedList().asLiveData()

    fun unFollowSeries(seriesId: String) {
        viewModelScope.launch {
            seriesFollowedRepository.unFollowSeries(seriesId)
        }
    }

}