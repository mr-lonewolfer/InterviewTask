/*
package com.nimesh.interviewtask.Utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.nimesh.interviewtask.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

*/
/**
 * Created by Nimesh Patel on 4/16/2024.
 * Purpose:
 *//*

fun retrieveFromRemote(context: Context, imageUrl: String, callback: (Bitmap?) -> Unit) {
    GlobalScope.launch(Dispatchers.IO) {
        try {
            val url = URL(imageUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()

            val bitmap: Bitmap? = if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val inputStream = connection.inputStream
                val bitmap = BitmapFactory.decodeStream(inputStream)
                inputStream.close()

                // Save to internal storage
                saveToInternalStorage(context, bitmap, imageUrl.hashCode().toString()) { isSaved ->
                    Log.e("neem", "Saved Successfully: ")
                }

                bitmap
            } else {
                null
            }

            // Invoke the callback with the retrieved bitmap
            callback(bitmap)
        } catch (e: IOException) {
            // Invoke the callback with null in case of an error
            callback(null)
        }
    }
}

fun saveToInternalStorage(
    context: Context,
    bitmap: Bitmap,
    filename: String,
    callback: (Boolean) -> Unit
) {
    GlobalScope.launch(Dispatchers.IO) {
        try {
            val fileOutputStream = FileOutputStream(File(context.filesDir, filename))
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
            fileOutputStream.close()
            // Invoke the callback with true indicating success
            callback(true)
        } catch (e: IOException) {
            // Invoke the callback with false indicating failure
            callback(false)
        }
    }
}

fun getImage(context: Context, imageUrl: String, callback: (Bitmap?) -> Unit) {
    val cacheDir = context.cacheDir
    val cachedFile = File(cacheDir, imageUrl.hashCode().toString())
    if (cachedFile.exists()) {
        callback(BitmapFactory.decodeFile(cachedFile.absolutePath))
    }

    // Check internal storage
    val internalStorageFile = File(context.filesDir, imageUrl.hashCode().toString())
    if (internalStorageFile.exists()) {
        callback(BitmapFactory.decodeFile(internalStorageFile.absolutePath))
    }

    retrieveFromRemote(context, imageUrl) { bitmap ->
        if (bitmap != null)
            callback(bitmap)
        else {
            val placeholderBitmap =
                BitmapFactory.decodeResource(context.resources, R.drawable.img_place_holder)
            callback(placeholderBitmap)
        }

    }
}

fun isValidImagePath(urlString: String, callback: (Boolean) -> Unit) {
    GlobalScope.launch(Dispatchers.IO) {
        try {
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "HEAD"
            connection.connect()

            val responseCode = connection.responseCode
            val contentType = connection.contentType

            connection.disconnect()

            val isImage = contentType?.startsWith("image/") ?: false

            // Callback to the UI thread with the result
            callback(responseCode == HttpURLConnection.HTTP_OK && isImage)
        } catch (e: Exception) {
            // URL is invalid or connection failed
            // Callback to the UI thread with the result
            callback(false)
        }
    }
}*/
