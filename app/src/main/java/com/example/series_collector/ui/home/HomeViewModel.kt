package com.example.series_collector.ui.home

import androidx.lifecycle.*
import androidx.work.WorkInfo
import com.example.series_collector.data.model.category.CategoryListItem
import com.example.series_collector.data.repository.CategoryRepository
import com.example.series_collector.workers.SeriesWorkerManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val seriesWorkerManager: SeriesWorkerManager,
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _categoryContents = MutableLiveData<List<CategoryListItem>>()
    val categoryContents: LiveData<List<CategoryListItem>> = _categoryContents

    init {
        updateSeries()
    }

    private var updateJob: Job? = null

    fun updateSeries() {
        updateJob?.cancel()
        updateJob = viewModelScope.launch {
            _isLoading.value = true
            seriesWorkerManager.update()
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

