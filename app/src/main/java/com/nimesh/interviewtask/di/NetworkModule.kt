package com.nimesh.interviewtask.di

import android.content.Context
import androidx.multidex.BuildConfig
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.nimesh.interviewtask.Utils.GlobalVariables
import com.nimesh.interviewtask.data.network.MediaCoverageRepository
import com.nimesh.interviewtask.data.network.MediaCoverageServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by Nimesh Patel on 4/16/2024.
 * Purpose: Providing dependencies related to network operations,
 * such as OkHttpClient, Retrofit, and MediaCoverageServices.
 */
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Named(GlobalVariables.BASE_URL_KEY)
    fun provideBaseUrl(): String {
        return GlobalVariables.BASE_URL
    }


    @Singleton
    @Provides
    fun provideOkHttpClient(context: Context): OkHttpClient {
        return if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            OkHttpClient.Builder()
                .cache(Cache(context.cacheDir, 10 * 1024 * 1024)) // Stored 10MB Cache
                .addInterceptor(loggingInterceptor)
                .build()
        } else {
            OkHttpClient
                .Builder()
                .build()
        }
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        @Named(GlobalVariables.BASE_URL_KEY) BASE_URL: String
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory()) // For Async Call
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    }


    @Singleton
    @Provides
    fun provideMediaCoverageService(retrofit: Retrofit): MediaCoverageServices =
        retrofit.create(MediaCoverageServices::class.java)


    @Singleton
    @Provides
    fun provideMediaCoverageRepository(
        context: Context,
        mediaCoverageServices: MediaCoverageServices
    ): MediaCoverageRepository = MediaCoverageRepository(context, mediaCoverageServices)


}