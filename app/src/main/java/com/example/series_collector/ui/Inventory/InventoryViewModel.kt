package com.example.series_collector.ui.Inventory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.series_collector.data.repository.SeriesFollowedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val seriesFollowedRepository: SeriesFollowedRepository
) : ViewModel() {
}