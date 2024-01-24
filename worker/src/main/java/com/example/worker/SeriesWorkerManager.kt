//package com.example.worker
//
//import android.content.Context
//import androidx.work.ExistingWorkPolicy
//import androidx.work.OneTimeWorkRequestBuilder
//import androidx.work.WorkInfo
//import androidx.work.WorkManager
//import androidx.work.workDataOf
//import dagger.hilt.android.qualifiers.ApplicationContext
//import kotlinx.coroutines.flow.Flow
//import java.util.Calendar
//import javax.inject.Inject
//
//interface SeriesWorkerManager {
//
//    fun setUpSeries(): Flow<WorkInfo>
//
//    fun updateSeries(updatedDate: Calendar?): Flow<WorkInfo>
//
//}
//
//class SeriesWorkerManagerImpl @Inject constructor(
//    @ApplicationContext private val appContext: Context
//) : SeriesWorkerManager {
//
//    private val workManager = WorkManager.getInstance(appContext)
//
//    override fun setUpSeries(): Flow<WorkInfo> {
//        TODO()
//    }
//
//    override fun updateSeries(updatedDate: Calendar?): Flow<WorkInfo> {
//        TODO()
//        val workerRequest = OneTimeWorkRequestBuilder<UpdateSeriesWorker>()
//            .build()
//
//        workManager
//            .beginUniqueWork("update_work", ExistingWorkPolicy.APPEND_OR_REPLACE, workerRequest)
//            .enqueue()
//        return workManager.getWorkInfoByIdLiveData(workerRequest.id).asFlow()
//    }
//
//}