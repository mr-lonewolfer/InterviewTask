package com.nimesh.interviewtask.Utils.worker

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by Nimesh Patel on 4/16/2024.
 * Purpose:
 */

class ImageLoaderWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val imageUrl = inputData.getString(KEY_IMAGE_URL)
        if (imageUrl.isNullOrEmpty()) {
            return Result.failure()
        }

        try {
            val bitmap = loadImageFromNetwork(imageUrl)
            if (bitmap != null) {
                // Pass the bitmap to the UI
                // For simplicity, let's log the bitmap for demonstration
                Log.d(TAG, "Bitmap loaded successfully: $bitmap")
                return Result.success()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error loading image: ${e.message}")
        }
        return Result.failure()
    }

    private fun loadImageFromNetwork(imageUrl: String): Bitmap? {
        var connection: HttpURLConnection? = null
        return try {
            val url = URL(imageUrl)
            connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val inputStream = connection.inputStream
            BitmapFactory.decodeStream(inputStream)
        } catch (e: IOException) {
            Log.e(TAG, "Error loading image: ${e.message}")
            null
        } finally {
            connection?.disconnect()
        }
    }

    companion object {
        private const val TAG = "ImageLoaderWorker"
        const val KEY_IMAGE_URL = "image_url"
    }
}