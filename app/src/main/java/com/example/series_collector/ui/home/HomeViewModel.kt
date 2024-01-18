package com.example.series_collector.ui.home

import androidx.lifecycle.*
import androidx.work.WorkInfo
import com.example.series_collector.data.model.ListItem
import com.example.series_collector.data.repository.CategoryRepository
import com.example.series_collector.data.repository.SeriesRepository
import com.example.series_collector.utils.workers.SeriesWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val seriesWorker: SeriesWorker,
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _categoryContents = MutableLiveData<List<ListItem>>()
    val categoryContents: LiveData<List<ListItem>> = _categoryContents

    init {
        updateSeries()
    }

    private var updateJob: Job? = null

    fun updateSeries() {
        updateJob?.cancel() // TODO
        updateJob = viewModelScope.launch {
            _isLoading.value = true
            seriesWorker.updateStream()
                .collect { workInfo ->
                    when (workInfo.state) {
                        WorkInfo.State.SUCCEEDED -> {
                            _categoryContents.value = categoryRepository.getCategoryContents()
                            _isLoading.value = false
                        }

                        WorkInfo.State.FAILED, WorkInfo.State.BLOCKED -> {
                            _isLoading.value = false
                        }
                    }
                }
        }
    }
}

