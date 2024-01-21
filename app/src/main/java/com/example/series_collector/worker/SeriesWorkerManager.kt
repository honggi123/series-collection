package com.example.series_collector.worker

import androidx.work.WorkInfo
import kotlinx.coroutines.flow.Flow


interface SeriesWorkerManager {

    fun update(): Flow<WorkInfo>

}
