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
import kotlinx.coroutines.flow.collectLatest
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
            val lastUpdateDate = userRepository.getLastUpdateDate()
            val updatedDate = lastUpdateDate ?: INITIAL_DATE
            updateSeries(updatedDate)
        }
    }

    private suspend fun updateSeries(updatedDate: Calendar) {
        val workerRequest = UpdateSeriesWorker.enqueue(workManager, updatedDate)
        val workInfo = workManager.getWorkInfoByIdLiveData(workerRequest.id).asFlow()
        workInfo.collectLatest {
            if (it.state == WorkInfo.State.SUCCEEDED) {
                userRepository.updateLastUpdateDate(Calendar.getInstance())
                _isLoading.value = false
            }
        }
    }

    companion object {
        val INITIAL_DATE: Calendar = Calendar.getInstance().apply {
            set(2020, Calendar.JANUARY, 1)
        }
    }
}