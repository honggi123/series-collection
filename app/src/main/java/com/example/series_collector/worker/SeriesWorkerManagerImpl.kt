package com.example.series_collector.worker

import android.content.Context
import androidx.lifecycle.asFlow
import androidx.work.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SeriesWorkerManagerImpl @Inject constructor(
    @ApplicationContext private val appContext: Context
) : SeriesWorkerManager {

    private val workManager = WorkManager.getInstance(appContext)

    override fun update(): Flow<WorkInfo> = updateSeriesStream(workManager)

    private fun updateSeriesStream(workManager: WorkManager): Flow<WorkInfo> {
        val workerRequest = OneTimeWorkRequestBuilder<SeriesUpdateWorker>().build()

        workManager
            .beginUniqueWork("update_work", ExistingWorkPolicy.APPEND_OR_REPLACE, workerRequest)
            .enqueue()

        return workManager.getWorkInfoByIdLiveData(workerRequest.id).asFlow()
    }
}
