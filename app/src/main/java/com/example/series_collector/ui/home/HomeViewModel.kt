package com.example.series_collector.ui.home

import android.content.Context
import androidx.lifecycle.*
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.series_collector.data.Category
import com.example.series_collector.data.Series
import com.example.series_collector.data.repository.SeriesRepository
import com.example.series_collector.utils.CATEGORY_FICTION
import com.example.series_collector.utils.CATEGORY_POPULAR
import com.example.series_collector.utils.CATEGORY_RECENT
import com.example.series_collector.utils.CATEGORY_TRAVEL
import com.example.series_collector.utils.workers.SeriesInitWorker.Companion.WORK_TAG
import com.example.series_collector.utils.workers.Workers
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val seriesRepository: SeriesRepository,
    private val workers: Workers,
    @ApplicationContext appContext: Context
) : ViewModel() {

    val outputInitWorkInfos: LiveData<List<WorkInfo>> = WorkManager.getInstance(appContext)
        .getWorkInfosByTagLiveData(WORK_TAG)

    private val _SeriesContents = MutableLiveData<List<Category>>()
    val SeriesContents: LiveData<List<Category>> = _SeriesContents

    init {
        viewModelScope.launch {
            seriesRepository.run {
                if (isEmpty()) {
                    workers.buildInitWorker(appContext)
                } else {
                    insertUpdatedSeries()
                }

                val list: MutableList<Category> = getCategorys()
                list.map {
                    it.seriesList = this.getCategoryList(it.categoryId)
                }

                _SeriesContents.value = list
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