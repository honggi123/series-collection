package com.example.series_collector.ui.home

import androidx.lifecycle.*
import com.example.series_collector.data.model.CategoryContent
import com.example.series_collector.data.repository.CategoryRepository
import com.example.series_collector.data.repository.SeriesRepository
import com.example.series_collector.utils.workers.SeriesWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val seriesRepository: SeriesRepository,
    private val categoryRepository: CategoryRepository,
    private val seriesWorker: SeriesWorker,
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _categoryContents = MutableLiveData<List<CategoryContent>>()
    val categoryContents: LiveData<List<CategoryContent>> = _categoryContents

    init {
        updateSeries()
    }

    private var updateJob: Job? = null

    fun updateSeries() {
        updateJob?.cancel()
        updateJob = viewModelScope.launch {
            _isLoading.value = true
            seriesWorker.updateStream()
                .collect { workInfo ->
                    if (workInfo.state.isFinished) {
                        // isFinished return : true for SUCCEEDED, FAILED, and CANCELLED states
                        refreshCategoryContents()
                        _isLoading.value = false
                    }
                }
        }
    }

    private suspend fun refreshCategoryContents() =
        withContext(Dispatchers.IO) {
            val categoryContents = categoryRepository.getCategoryContents()
            _categoryContents.postValue(categoryContents)
        }



}

