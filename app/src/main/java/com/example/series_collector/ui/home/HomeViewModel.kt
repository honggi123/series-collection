package com.example.series_collector.ui.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.example.series_collector.data.Category
import com.example.series_collector.data.Series
import com.example.series_collector.data.repository.CategoryRepository
import com.example.series_collector.data.repository.SeriesRepository
import com.example.series_collector.utils.CATEGORY_FICTION
import com.example.series_collector.utils.CATEGORY_POPULAR
import com.example.series_collector.utils.CATEGORY_RECENT
import com.example.series_collector.utils.CATEGORY_TRAVEL
import com.example.series_collector.utils.workers.Workers
import com.example.series_collector.utils.workers.Workers.Companion.SYNC_WORK_TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val seriesRepository: SeriesRepository,
    private val categoryRepository: CategoryRepository,
    private val workers: Workers,
    @ApplicationContext private val appContext: Context
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _seriesContents = MutableLiveData<List<Category>>()
    val seriesContents: LiveData<List<Category>> = _seriesContents

    init {
        cancelAllWork()
        viewModelScope.launch {
            _isLoading.value = true
            workers.run {
                if (seriesRepository.isEmpty()) {
                    buildInitWorker(appContext)
                } else {
                    buildUpdateWorker(appContext)
                }
            }.also {
                WorkManager.getInstance(appContext)
                    .getWorkInfoByIdLiveData(it).asFlow().collect { workInfo ->
                        if (workInfo.state.isFinished) {
                            refreshCategorys()
                            _isLoading.value = false
                        }
                    }
            }
        }
    }


    private fun cancelAllWork() {
        WorkManager.getInstance(appContext).cancelAllWorkByTag(SYNC_WORK_TAG)
    }

    private fun refreshCategorys() {
        viewModelScope.launch {
            val categorys: MutableList<Category> = categoryRepository.getCategorys()

            categorys.map { category ->
                category.seriesList = getCategoryList(category.categoryId)
            }
            _seriesContents.value = categorys
        }
    }

    private suspend fun getCategoryList(categoryId: Int): List<Series> =
        withContext(Dispatchers.IO) {
            categoryRepository.getCategoryList(categoryId)
                .map { series ->
                    async {
                        series.apply {
                            if (thumbnail.isNullOrEmpty()) {
                                thumbnail = seriesRepository.getThumbnailImage(series.seriesId)
                                insertSeries(series)
                            }
                        }
                    }
                }.awaitAll()
        }

    private fun insertSeries(series: Series) {
        viewModelScope.launch {
            seriesRepository.insertSeries(series)
        }
    }


}