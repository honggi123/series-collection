package com.example.series_collector.ui.home

import android.util.Log
import androidx.lifecycle.*
import com.example.series_collector.data.model.CategoryContent
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
    private val seriesRepository: SeriesRepository,
    private val categoryRepository: CategoryRepository,
    private val seriesWorker: SeriesWorker,
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val isUpdateFinished: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val categoryContents: LiveData<List<ListItem>> = isUpdateFinished
        .filter { it == false }
        .flatMapLatest {
            categoryRepository.getCategoryContentsStream(
                onComplete = { _isLoading.postValue(false) }
            )
        }.asLiveData()

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
                    isUpdateFinished.value = workInfo.state.isFinished
                }
        }
    }


}

