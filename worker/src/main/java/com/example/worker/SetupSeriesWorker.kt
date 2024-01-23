//package com.example.worker
//
//import android.content.Context
//import androidx.hilt.work.HiltWorker
//import androidx.work.CoroutineWorker
//import androidx.work.WorkerParameters
//import com.example.data.source.local.SeriesLocalDataSource
//import com.example.data.source.network.SeriesNetworkDataSource
//import com.example.worker.util.SeriesThumbnailFetcher
//import dagger.assisted.Assisted
//import dagger.assisted.AssistedInject
//import kotlinx.coroutines.supervisorScope
//
//// TODO
//@HiltWorker
//class SetupSeriesWorker @AssistedInject constructor(
//    @Assisted context: Context,
//    @Assisted workerParams: WorkerParameters,
//    private val seriesLocalDataSource: SeriesLocalDataSource,
//    private val seriesNetworkDataSource: SeriesNetworkDataSource,
//    private val seriesThumbnailFetcher: SeriesThumbnailFetcher,
//) : CoroutineWorker(context, workerParams) {
//    override suspend fun doWork(): Result = supervisorScope {
//        try {
//            val seriesList = seriesNetworkDataSource.getAllSeries()
//            val list = seriesThumbnailFetcher(seriesList)
//            seriesLocalDataSource.insertSeriesList(list)
//            Result.success()
//        } catch (ex: Exception) {
//            Result.failure()
//        }
//    }
//}