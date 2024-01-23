//package com.example.worker.util//package com.example.worker
//
//import android.content.Context
//import androidx.work.*
//import com.example.worker.UpdateSeriesWorker
//import dagger.hilt.android.qualifiers.ApplicationContext
//import kotlinx.coroutines.flow.Flow
//import javax.inject.Inject
//
//interface SeriesWorkerManager {
//
//    fun updateSeries(): Flow<WorkInfo>
//
//    fun setUpSeries(): Flow<WorkInfo>
//
//}
//
//class SeriesWorkerManagerImpl @Inject constructor(
//    @ApplicationContext private val appContext: Context
//) : SeriesWorkerManager {
//
//    private val workManager = WorkManager.getInstance(appContext)
//
//    override fun updateSeries(): Flow<WorkInfo> {
//        val workerRequest = OneTimeWorkRequestBuilder<UpdateSeriesWorker>().build()
//        workManager
//            .beginUniqueWork("update_work", ExistingWorkPolicy.APPEND_OR_REPLACE, workerRequest)
//            .enqueue()
//        return workManager.getWorkInfoByIdLiveData(workerRequest.id).asFlow()
//    }
//
//    override fun setUpSeries(): Flow<WorkInfo> {
//        TODO("Not yet implemented")
//    }
//
//}