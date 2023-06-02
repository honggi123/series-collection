package com.example.series_collector.ui.home

import androidx.lifecycle.*
import com.example.series_collector.data.model.CategoryContent
import com.example.series_collector.data.repository.CategoryRepository
import com.example.series_collector.data.repository.SeriesRepository
import com.example.series_collector.utils.workers.SeriesWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val seriesRepository: SeriesRepository,
    private val categoryRepository: CategoryRepository,
    private val seriesWorker: SeriesWorker,
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val workState: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val categoryContents: LiveData<List<CategoryContent>> = workState.flatMapLatest { isFinished ->
        if (isFinished) {
            categoryRepository.getCategoryContentsStream(
                onComplete = { _isLoading.postValue(false) }
            )
        } else {
            flow { emit(emptyList()) }
        }
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
                    workState.value = workInfo.state.isFinished
                }
        }
    }


}

