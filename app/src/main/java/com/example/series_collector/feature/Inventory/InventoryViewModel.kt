package com.example.series_collector.feature.Inventory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val seriesRepository: com.example.data.repository.SeriesRepository
) : ViewModel() {

    val mySeriesList = seriesRepository.getFollowingSeriesList().asLiveData()

    fun unFollowSeries(seriesId: String) {
        viewModelScope.launch {
            seriesRepository.unFollowSeries(seriesId)
        }
    }

}