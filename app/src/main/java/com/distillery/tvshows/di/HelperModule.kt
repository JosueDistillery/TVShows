package com.distillery.tvshows.di

import android.content.Context
import android.net.ConnectivityManager
import com.distillery.tvshows.utils.helpers.ConnectivityHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
        @ApplicationContext context: Context
    ): ConnectivityHelper {
        return ConnectivityHelper.build(context)
    }
}