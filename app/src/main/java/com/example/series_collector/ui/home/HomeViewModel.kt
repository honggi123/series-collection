package com.example.series_collector.ui.home

import android.content.Context
import androidx.lifecycle.*
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.series_collector.data.Series
import com.example.series_collector.data.repository.SeriesRepository
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

    init {
        viewModelScope.launch {
            if (seriesRepository.isEmpty()) {
                workers.buildInitWorker(appContext)
            } else {
                insertUpdatedSeries()
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