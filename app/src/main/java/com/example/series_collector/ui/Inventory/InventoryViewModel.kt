package com.example.series_collector.ui.Inventory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    val mySeriesList = userRepository.getFollowingSeriesList()
        .asLiveData()

    fun unFollowSeries(seriesId: String) {
        viewModelScope.launch {
            userRepository.setSeriesUnFollowed(seriesId)
        }
    }
}