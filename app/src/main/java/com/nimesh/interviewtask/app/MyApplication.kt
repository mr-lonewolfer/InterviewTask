package com.nimesh.interviewtask.app

import android.os.StrictMode
import androidx.hilt.work.HiltWorkerFactory
import androidx.multidex.MultiDexApplication
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * Created by Nimesh Patel on 4/16/2024.
 * Purpose:
 */
@HiltAndroidApp
class MyApplication : MultiDexApplication() , Configuration.Provider{

    @Inject
    lateinit var vmPolicy: StrictMode.VmPolicy

    @Inject
    lateinit var hiltWorkerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        StrictMode.setVmPolicy(vmPolicy)
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .setWorkerFactory(hiltWorkerFactory)
            .build()


}