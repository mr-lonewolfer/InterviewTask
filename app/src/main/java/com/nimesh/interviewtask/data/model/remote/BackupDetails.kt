package com.nimesh.interviewtask.data.model.remote


import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class BackupDetails(
    val pdfLink: String,
    val screenshotURL: String
) : Parcelable