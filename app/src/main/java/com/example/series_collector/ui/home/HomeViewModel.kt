package com.example.series_collector.ui.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.series_collector.data.Series
import com.example.series_collector.data.repository.SeriesRepository
import com.example.series_collector.utils.workers.SeriesInitWorker
import com.example.series_collector.utils.workers.SeriesInitWorker.Companion.WORK_TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val seriesRepository: SeriesRepository,
    @ApplicationContext appContext: Context
) : ViewModel() {

    val outputInitWorkInfos: LiveData<List<WorkInfo>> = WorkManager.getInstance(appContext)
        .getWorkInfosByTagLiveData(WORK_TAG)

    init {
        viewModelScope.launch {
            if (seriesRepository.isEmpty()) {
                val request = OneTimeWorkRequestBuilder<SeriesInitWorker>()
                    .addTag(WORK_TAG)
                    .build()
                WorkManager.getInstance(appContext).enqueue(request)
            } else {
                insertUpdatedSeries()
            }
        }
    }


    private fun insertUpdatedSeries() {
        viewModelScope.launch {
            val lastDate = seriesRepository.getLastUpdatedSeries()?.lastUpdateDate

            seriesRepository.getUpdatedSeries(lastDate)?.map {
                insertSeries(it)
            }
        }
    }

    private fun insertSeries(taskAsync: List<Series>) =
        viewModelScope.launch {
            seriesRepository.insertAllSeries(taskAsync)
        }


}