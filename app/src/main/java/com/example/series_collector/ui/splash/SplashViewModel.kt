package com.example.series_collector.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.data.repository.SeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val seriesRepository: SeriesRepository,
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        // TODO
        _isLoading.value = true
        val isEmpty = seriesRepository.isEmpty()
        if (isEmpty) {

        } else {

        }
    }

//    private fun updateSeries() {
//        updateJob?.cancel()
//        updateJob = viewModelScope.launch {
//            _isLoading.value = true
//            seriesWorkerManager.update()
//                .collect { workInfo ->
//                    when (workInfo.state) {
//                        WorkInfo.State.SUCCEEDED -> {
//                            _isLoading.value = false
//                        }
//
//                        WorkInfo.State.FAILED, WorkInfo.State.BLOCKED -> {
//                            _isLoading.value = false
//                        }
//                    }
//                }
//        }
//    }
}