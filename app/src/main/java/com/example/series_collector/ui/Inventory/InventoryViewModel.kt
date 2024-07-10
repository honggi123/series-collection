package com.example.series_collector.ui.Inventory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private var _errorMsg = MutableLiveData<String?>()
    val errorMsg: LiveData<String?> = _errorMsg

    val mySeriesList = userRepository.getFollowingSeriesList()
        .catch { _errorMsg.value = "데이터를 가져오는데 실패했습니다." }
        .asLiveData()

    fun unFollowSeries(
        seriesId: String,
    ) {
        viewModelScope.launch {
            userRepository.setSeriesUnFollowed(seriesId)
        }
    }
}