package com.nimesh.interviewtask.di

import android.app.Application
import android.content.Context
import android.os.StrictMode
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Nimesh Patel on 4/16/2024.
 * Purpose: used for providing application-level dependencies.
 * Such As, Application Context, StrictMode Policy
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    //Application level context
    @Singleton
    @Provides
    fun provideApplicationContext(app: Application): Context = app


    // get Strict mode policy
    @Singleton
    @Provides
    fun provideVmPolicy(): StrictMode.VmPolicy =
        StrictMode.VmPolicy.Builder()
            .detectFileUriExposure()
            .build()


}