package com.nimesh.interviewtask.app

import android.os.StrictMode
import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * Created by Nimesh Patel on 4/16/2024.
 * Purpose: Enable Hilt dependency injection.
 * It configures StrictMode policies.
 */
@HiltAndroidApp
class MyApplication : MultiDexApplication() {

    @Inject
    lateinit var vmPolicy: StrictMode.VmPolicy

    override fun onCreate() {
        super.onCreate()
        StrictMode.setVmPolicy(vmPolicy)
    }
}