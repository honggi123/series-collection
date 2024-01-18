package com.example.series_collector.workers

import androidx.work.WorkInfo
import kotlinx.coroutines.flow.Flow


interface SeriesWorkerManager {

    fun update(): Flow<WorkInfo>

}
