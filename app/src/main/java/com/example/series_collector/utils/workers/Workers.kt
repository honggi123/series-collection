package com.example.series_collector.utils.workers

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import javax.inject.Inject

class Workers @Inject constructor() {
    fun buildInitWorker(context: Context) {
        val request = OneTimeWorkRequestBuilder<SeriesInitWorker>()
            .addTag(SeriesInitWorker.WORK_TAG)
            .build()
        WorkManager.getInstance(context).enqueue(request)
    }
}