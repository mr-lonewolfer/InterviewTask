package com.nimesh.interviewtask.di

import androidx.multidex.BuildConfig
import com.nimesh.interviewtask.Utils.GlobalVariables
import com.nimesh.interviewtask.data.network.MediaCoverageRepository
import com.nimesh.interviewtask.data.network.MediaCoverageServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by Nimesh Patel on 4/16/2024.
 * Purpose:
 */
@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Named(GlobalVariables.BASE_URL_KEY)
    fun provideBaseUrl(): String {
        return GlobalVariables.BASE_URL
    }


    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            OkHttpClient.Builder()
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
        mediaCoverageServices: MediaCoverageServices
    ): MediaCoverageRepository = MediaCoverageRepository(mediaCoverageServices)


}