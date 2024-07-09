package com.example.series_collector.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.data.repository.UserRepository
import com.example.worker.UpdateSeriesWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val workManager: WorkManager
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        viewModelScope.launch {
            if (userRepository.isEmpty()) { // 로컬 데이터가 존재하지 않을 경우 (처음 초기화)
                updateSeries(Calendar.getInstance())
            } else {
                val updatedDate = userRepository.getLastUpdateDate()
                updateSeries(updatedDate)
            }
        }
    }

    private fun updateSeries(updatedDate: Calendar?) {
        viewModelScope.launch {
            val workerRequest = UpdateSeriesWorker.enqueue(workManager, updatedDate!!)
            val workInfo = workManager.getWorkInfoByIdLiveData(workerRequest.id).asFlow()
            workInfo.collect {
                if (it.state == WorkInfo.State.SUCCEEDED) {
                    userRepository.updateLastUpdateDate(Calendar.getInstance())
                }
                _isLoading.value = false
            }
        }
    }
}