package com.example.series_collector.utils.workers

import android.content.Context
import android.util.Log
import androidx.lifecycle.asFlow
import androidx.work.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface SeriesWorker {
    fun updateStream(): Flow<WorkInfo>
}

class SeriesWorkerImpl @Inject constructor(
    @ApplicationContext private val appContext: Context
) : SeriesWorker {

    private val workManager = WorkManager.getInstance(appContext)
    private val workerRequest = OneTimeWorkRequestBuilder<SeriesUpdateWorker>().build()

    override fun updateStream(): Flow<WorkInfo> = updateSeriesStream(workManager)

    private fun updateSeriesStream(workManager: WorkManager): Flow<WorkInfo> {
        workManager
            .beginUniqueWork("update_work", ExistingWorkPolicy.APPEND_OR_REPLACE, workerRequest)
            .enqueue()

        return workManager.getWorkInfoByIdLiveData(workerRequest.id).asFlow()
    }

}
