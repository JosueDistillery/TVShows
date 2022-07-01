package com.distillery.tvshows.di

import com.distillery.tvshows.BuildConfig
import com.distillery.tvshows.database.converters.GenresConverter
import com.distillery.tvshows.network.TVApi
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    /**
     * Provide [Gson]
     */
    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    /**
     * Provide [GsonConverterFactory]
     */
    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory = GsonConverterFactory.create(gson)

    /**
     * Provide [HttpLoggingInterceptor]
     */
    @Provides
    @Singleton
    fun provideLoggingInterceptor() =
        HttpLoggingInterceptor().apply { level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE }

    /**
     * Build [Retrofit]
     */
    @Provides
    @Singleton
    fun provideRetrofit(
        httpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.TVMAZE_BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(httpClient)
            .build()

    /**
     * Build [OkHttpClient]
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()

    /**
     * Create [TVApi]
     */
    @Provides
    @Singleton
    fun provideTVApi(retrofit: Retrofit): TVApi = TVApi.create(retrofit)
}