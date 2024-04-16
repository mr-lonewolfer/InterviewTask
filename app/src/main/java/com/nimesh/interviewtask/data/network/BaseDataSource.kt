package com.nimesh.interviewtask.data.network

import com.nimesh.interviewtask.Utils.ApiState
import retrofit2.Response

/**
 * Created by Nimesh Patel on 4/16/2024.
 * Purpose:
 */
abstract class BaseDataSource {
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): ApiState<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return ApiState.success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): ApiState<T> {
        return ApiState.error("Network call failed due to: $message")
    }

}