package com.example.series_collector.ui.home

import android.content.Context
import androidx.lifecycle.*
import androidx.work.WorkManager
import androidx.work.await
import com.example.series_collector.data.Category
import com.example.series_collector.data.Series
import com.example.series_collector.data.repository.CategoryRepository
import com.example.series_collector.data.repository.SeriesRepository
import com.example.series_collector.utils.workers.SeriesWork
import com.example.series_collector.utils.workers.SeriesWorkImpl.Companion.SYNC_WORK_TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val seriesRepository: SeriesRepository,
    private val categoryRepository: CategoryRepository,
    private val seriesWork: SeriesWork,
    @ApplicationContext private val appContext: Context
) : ViewModel() {

    private val workManager = WorkManager.getInstance(appContext)

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _seriesContents = MutableLiveData<List<Category>>()
    val seriesContents: LiveData<List<Category>> = _seriesContents

    init {
        _isLoading.value = true
        viewModelScope.launch {
            cancelAllWork()
            seriesWork.run {
                if (seriesRepository.isEmpty()) {
                    initSeries()
                } else {
                    updateSeries()
                }
            }.also {
                workManager
                    .getWorkInfoByIdLiveData(it).asFlow().collect { workInfo ->
                        if (workInfo.state.isFinished) {
                            refreshSeriesContents()
                            _isLoading.postValue(false)
                        }
                    }
            }
        }
    }


    private suspend fun cancelAllWork() {
        workManager.cancelAllWorkByTag(SYNC_WORK_TAG).await()
    }

    private suspend fun refreshSeriesContents() {
        withContext(Dispatchers.IO) {
            val categorys: MutableList<Category> = categoryRepository.getCategorys()

            val seriesContents = categorys.map { category ->
                category.copy(seriesList = getSeriesList(category.categoryId))
            }.toList()

            _seriesContents.postValue(seriesContents)
        }
    }


    private suspend fun getSeriesList(categoryId: Int): List<Series> =
        categoryRepository.getCategoryList(categoryId)


}