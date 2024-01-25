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
import com.example.worker.UpdateSeriesWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val seriesRepository: SeriesRepository,
    private val workManager: WorkManager
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        viewModelScope.launch {
            if (seriesRepository.isEmpty()) {
                setUpSeries()
            } else {
                updateSeries()
            }
        }
    }

    private fun setUpSeries() {
        viewModelScope.launch {
            val workerRequest = SetupSeriesWorker.enqueue(workManager)
            val workInfo = workManager.getWorkInfoByIdLiveData(workerRequest.id).asFlow()
            workInfo.collect {
                if (it.state == WorkInfo.State.SUCCEEDED) {
                    seriesRepository.lastUpdateDate(Calendar.getInstance())
                    _isLoading.value = false
                } else if (it.state == WorkInfo.State.FAILED) {
                    _isLoading.value = false
                }
            }
        }
    }

    private fun updateSeries() {
        viewModelScope.launch {
            val updateDate = seriesRepository.getLastUpdateDate()
            val workerRequest = UpdateSeriesWorker.enqueue(workManager, updateDate!!)
            val workInfo = workManager.getWorkInfoByIdLiveData(workerRequest.id).asFlow()
            workInfo.collect {
                if (it.state == WorkInfo.State.SUCCEEDED) {
                    seriesRepository.lastUpdateDate(Calendar.getInstance())
                    _isLoading.value = false
                } else if (it.state == WorkInfo.State.FAILED) {
                    _isLoading.value = false
                }
            }
        }
    }

}