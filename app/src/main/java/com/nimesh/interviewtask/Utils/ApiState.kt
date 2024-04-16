package com.nimesh.interviewtask.Utils

/**
 * Created by Nimesh Patel on 4/16/2024.
 * Purpose:
 */
data class ApiState<out T>(val status: Status, val data: T?, val message: String?) {
    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): ApiState<T> {
            return ApiState(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null): ApiState<T> {
            return ApiState(Status.ERROR, data, message)
        }

        fun <T> loading(data: T? = null): ApiState<T> {
            return ApiState(Status.LOADING, data, null)
        }
    }
}