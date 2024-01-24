package com.example.series_collector.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import com.example.data.repository.SeriesRepository
import com.example.worker.SeriesWorkerManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val seriesRepository: SeriesRepository,
    private val seriesWorkerManager: SeriesWorkerManager
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
        executeWorker(
            workerOperation = { seriesWorkerManager.setUpSeries() },
            onSuccess = {
                _isLoading.value = false
            },
            onFailure = {
                _isLoading.value = false
            }
        )
    }

    private fun updateSeries() {
        val updateDate = seriesRepository.getLastUpdateDate()

        executeWorker(
            workerOperation = { seriesWorkerManager.updateSeries(updateDate) },
            onSuccess = {
                // TODO save update date
                _isLoading.value = false
            },
            onFailure = {
                _isLoading.value = false
            }
        )
    }

    private fun executeWorker(
        workerOperation: () -> Flow<WorkInfo>,
        onSuccess: () -> Unit = {},
        onFailure: () -> Unit = {}
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            workerOperation()
                .collect {
                    if (it.state == WorkInfo.State.SUCCEEDED) {
                        onSuccess()
                    } else if (it.state == WorkInfo.State.FAILED) {
                        onFailure()
                    }
                }
        }
    }
}