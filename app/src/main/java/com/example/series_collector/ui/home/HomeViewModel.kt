package com.example.series_collector.ui.home

import android.content.Context
import androidx.lifecycle.*
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.series_collector.data.Category
import com.example.series_collector.data.Series
import com.example.series_collector.data.repository.CategoryRepository
import com.example.series_collector.data.repository.SeriesRepository
import com.example.series_collector.utils.CATEGORY_FICTION
import com.example.series_collector.utils.CATEGORY_POPULAR
import com.example.series_collector.utils.CATEGORY_RECENT
import com.example.series_collector.utils.CATEGORY_TRAVEL
import com.example.series_collector.utils.workers.SeriesInitWorker.Companion.WORK_TAG
import com.example.series_collector.utils.workers.Workers
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val seriesRepository: SeriesRepository,
    private val categoryRepository: CategoryRepository,
    private val workers: Workers,
    @ApplicationContext appContext: Context
) : ViewModel() {

    val outputInitWorkInfos: LiveData<List<WorkInfo>> = WorkManager.getInstance(appContext)
        .getWorkInfosByTagLiveData(WORK_TAG)

    private val _seriesContents = MutableLiveData<List<Category>>()
    val seriesContents: LiveData<List<Category>> = _seriesContents

    init {
        viewModelScope.launch {
            seriesRepository.run {
                if (isEmpty()) {
                    workers.buildInitWorker(appContext)
                } else {
                    insertUpdatedSeries()
                }

                val list: MutableList<Category> = categoryRepository.getCategorys()

                list.map { category ->
                    category.seriesList = getCategoryList(category.categoryId)
                }
                _seriesContents.value = list
            }
        }
    }

    private suspend fun getSeriesList(categoryId: Int): List<Series> =
        withContext(Dispatchers.IO) {
            categoryRepository.getCategoryList(categoryId)
                .map { series ->
                    series.apply {
                        if (thumbnail.isNullOrEmpty()) {
                            thumbnail = seriesRepository.getThumbnailImage(series.SeriesId)
                            seriesRepository.insertSeries(series)
                        }
                    }
                }
        }


    private fun insertUpdatedSeries() {
        viewModelScope.launch {
            val lastDate = seriesRepository.getLastUpdateDate()
            val list = seriesRepository.getUpdatedSeries(lastDate)
            insertSeries(list)
        }
    }

    private fun insertSeries(taskAsync: List<Series>) =
        viewModelScope.launch {
            seriesRepository.insertAllSeries(taskAsync)
        }


}