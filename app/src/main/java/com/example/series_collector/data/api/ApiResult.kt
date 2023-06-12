package com.example.series_collector.data.api

import java.io.IOException


sealed class ApiResult<out T> {
    data class Success<out T>(val value: T): ApiResult<T>()
    data class Failure(val code: Int, val msg: String?) : ApiResult<Nothing>()
    data class NetworkError(val exception: IOException) : ApiResult<Nothing>()
    data class Unexpected(val t: Throwable?) : ApiResult<Nothing>()
}

