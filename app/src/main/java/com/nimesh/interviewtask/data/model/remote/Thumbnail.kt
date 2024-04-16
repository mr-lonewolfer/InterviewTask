package com.nimesh.interviewtask.data.model.remote


import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class Thumbnail(
    val aspectRatio: Int,
    val basePath: String,
    val domain: String,
    val id: String,
    val key: String,
    val qualities: List<Int>,
    val version: Int
) : Parcelable