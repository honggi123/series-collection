package com.example.series_collector.ui.Inventory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.series_collector.data.repository.SeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val seriesRepository: SeriesRepository
) : ViewModel() {

    val mySeriesList = seriesRepository.getFollowingSeriesList().asLiveData()

    fun unFollowSeries(seriesId: String) {
        viewModelScope.launch {
            seriesRepository.unFollowSeries(seriesId)
        }
    }

}