package com.nimesh.interviewtask.Utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.ImageView
import com.nimesh.interviewtask.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by Nimesh Patel on 4/16/2024.
 * Purpose:
 */
fun ImageView.loadImageFromUrl(context: Context, imageUrl: String) {
    // Check if the URL is a valid image path
    isValidImagePath(imageUrl) { isValid ->
        if (isValid) {
            val cacheDir = context.cacheDir
            val cachedFile = File(cacheDir, imageUrl.hashCode().toString())

            // If image exists in cache, load it
            if (cachedFile.exists()) {
                setImageBitmap(BitmapFactory.decodeFile(cachedFile.absolutePath))
                return@isValidImagePath
            }

            // Check internal storage
            val internalStorageFile = File(context.filesDir, imageUrl.hashCode().toString())
            if (internalStorageFile.exists()) {
                setImageBitmap(BitmapFactory.decodeFile(internalStorageFile.absolutePath))
                return@isValidImagePath
            }

            // Retrieve from remote and save to cache or internal storage
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val url = URL(imageUrl)
                    val connection = url.openConnection() as HttpURLConnection
                    connection.doInput = true
                    connection.connect()

                    if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                        val inputStream = connection.inputStream
                        val bitmap = BitmapFactory.decodeStream(inputStream)
                        inputStream.close()

                        // Save to cache
                        saveToCache(context, bitmap, imageUrl.hashCode().toString())

                        // Display the image
                        GlobalScope.launch(Dispatchers.Main) {
                            setImageBitmap(bitmap)
                        }
                    }
                } catch (e: IOException) {
                    Log.e("ImageLoading", "Error loading image from URL: $imageUrl")
                }
            }
        } else {
            // Invalid image URL, load placeholder
            setImageResource(R.drawable.img_place_holder)
        }
    }
}

private fun saveToCache(context: Context, bitmap: Bitmap, filename: String) {
    try {
        val fileOutputStream = FileOutputStream(File(context.cacheDir, filename))
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
        fileOutputStream.close()
    } catch (e: IOException) {
        Log.e("ImageLoading", "Error saving image to cache: $e")
    }
}

 private fun isValidImagePath(urlString: String, callback: (Boolean) -> Unit) {
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
            GlobalScope.launch(Dispatchers.Main) {
                callback(responseCode == HttpURLConnection.HTTP_OK && isImage)
            }
        } catch (e: Exception) {
            // URL is invalid or connection failed
            // Callback to the UI thread with the result
            GlobalScope.launch(Dispatchers.Main) {
                callback(false)
            }
        }
    }
}