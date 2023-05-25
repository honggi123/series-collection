package com.example.series_collector.ui.home

import androidx.lifecycle.*
import com.example.series_collector.data.entitiy.Category
import com.example.series_collector.data.entitiy.Series
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

    private val _seriesContents = MutableLiveData<List<Category>>()
    val seriesContents: LiveData<List<Category>> = _seriesContents

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
                        refreshSeriesContents()
                        _isLoading.value = false
                    }
                }
        }
    }

    private suspend fun refreshSeriesContents() {
        withContext(Dispatchers.IO) {
            val categorys: MutableList<Category> = categoryRepository.getCategorys()

            val seriesContents =
                categorys.map { category ->
                    category.copy(seriesList = getSeriesByCategory(category.categoryId))
                }.toList()

            _seriesContents.postValue(seriesContents)
        }
    }


    private suspend fun getSeriesByCategory(categoryId: String): List<Series> =
        kotlin.runCatching {
            categoryRepository.getSeriesByCategory(categoryId)
        }.onFailure {
            it.printStackTrace()
        }.getOrDefault(emptyList())


}

