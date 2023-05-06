package com.example.series_collector.utils.workers

import android.content.Context
import androidx.lifecycle.asFlow
import androidx.lifecycle.map
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.await
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

interface SeriesWork {
    fun updateSeriesStream(): Flow<WorkInfo>
}

class SeriesWorkImpl @Inject constructor(
    @ApplicationContext private val appContext: Context
) : SeriesWork {

    private val workManager = WorkManager.getInstance(appContext)

    override fun updateSeriesStream(): Flow<WorkInfo> = requestUpdateWorker(appContext)

    private fun requestUpdateWorker(context: Context): Flow<WorkInfo> {
        cancelAllWork()
        val request = OneTimeWorkRequestBuilder<SeriesUpdateWorker>()
            .addTag(SYNC_WORK_TAG)
            .build()
        WorkManager.getInstance(context).enqueue(request)
        return workManager.getWorkInfoByIdLiveData(request.id).asFlow()
    }

    private fun cancelAllWork() {
        workManager.cancelAllWorkByTag(SYNC_WORK_TAG)
    }

    companion object {
        const val SYNC_WORK_TAG = "SeriesSyncWorker"
    }


}