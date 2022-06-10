package com.distillery.tvshows.di

import android.net.ConnectivityManager
import com.distillery.tvshows.utils.helpers.ConnectivityHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HelperModule {
    /**
     * Build [ConnectivityHelper]
     */
    @Provides
    @Singleton
    fun provideConnectivityHelper(
        connectivityManager: ConnectivityManager
    ): ConnectivityHelper {
        return ConnectivityHelper.build(connectivityManager)
    }
}