package com.example.series_collector.utils.workers

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject

interface SeriesWork {
    fun initSeries(): UUID
    fun updateSeries(): UUID
}

class SeriesWorkImpl @Inject constructor(
    @ApplicationContext private val appContext: Context
) : SeriesWork {
    override fun initSeries(): UUID = buildInitWorker(appContext)

    override fun updateSeries(): UUID = buildUpdateWorker(appContext)


    private fun buildInitWorker(context: Context): UUID {
        val request = OneTimeWorkRequestBuilder<SeriesInitWorker>()
            .addTag(SYNC_WORK_TAG)
            .build()
        WorkManager.getInstance(context).enqueue(request)
        return request.id
    }

    private fun buildUpdateWorker(context: Context): UUID {
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