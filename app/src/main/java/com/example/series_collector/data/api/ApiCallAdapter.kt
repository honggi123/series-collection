package com.example.series_collector.data.api

import retrofit2.CallAdapter
import retrofit2.Call
import java.lang.reflect.Type

class ApiCallAdapter(
    private val type: Type
) : CallAdapter<Type, Call<ApiResponse<Type>>> {
    override fun responseType(): Type = type

    override fun adapt(call: Call<Type>): Call<ApiResponse<Type>> {
        return ApiCall(call)
    }
}