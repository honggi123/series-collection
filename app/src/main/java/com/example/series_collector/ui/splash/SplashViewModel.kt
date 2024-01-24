package com.example.series_collector.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.data.repository.SeriesRepository
import com.example.worker.SetupSeriesWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val seriesRepository: SeriesRepository,
    private val workManager: WorkManager
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        if (seriesRepository.isEmpty()) {
            setUpSeries()
        } else {
            updateSeries()
        }
    }

    private fun setUpSeries() {
        val workerRequest = SetupSeriesWorker.enqueue(workManager)
        val workInfo = workManager.getWorkInfoByIdLiveData(workerRequest.id).asFlow()
        viewModelScope.launch {
            workInfo.collect {
                if (it.state == WorkInfo.State.SUCCEEDED) {
                    _isLoading.value = false
                } else if (it.state == WorkInfo.State.FAILED) {
                    _isLoading.value = false
                }
            }
        }
    }

    private fun updateSeries() {
        TODO()
    }

}