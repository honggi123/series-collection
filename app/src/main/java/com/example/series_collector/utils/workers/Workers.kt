package com.example.series_collector.utils.workers

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.util.*
import javax.inject.Inject

class Workers @Inject constructor() {
    fun buildInitWorker(context: Context): UUID {
        val request = OneTimeWorkRequestBuilder<SeriesInitWorker>()
            .addTag(SYNC_WORK_TAG)
            .build()
        WorkManager.getInstance(context).enqueue(request)
        return request.id
    }

    fun buildUpdateWorker(context: Context): UUID {
        val request = OneTimeWorkRequestBuilder<SeriesUpdateWorker>()
            .addTag(SYNC_WORK_TAG)
            .build()
        WorkManager.getInstance(context).enqueue(request)
        return request.id
    }

    companion object {
        const val SYNC_WORK_TAG = "SeriesSyncWorker"
    }
}