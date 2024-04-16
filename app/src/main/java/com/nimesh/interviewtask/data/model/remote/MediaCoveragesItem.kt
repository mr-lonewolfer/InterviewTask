package com.nimesh.interviewtask.data.model.remote


import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class MediaCoveragesItem(
    val backupDetails: BackupDetails?= null,
    val coverageURL: String,
    val id: String,
    val language: String,
    val mediaType: Int,
    val publishedAt: String,
    val publishedBy: String,
    val thumbnail: Thumbnail,
    val title: String
) : Parcelable