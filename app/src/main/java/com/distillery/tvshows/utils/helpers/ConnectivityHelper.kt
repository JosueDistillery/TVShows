package com.distillery.tvshows.utils.helpers

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.distillery.tvshows.utils.extensions.isNetConnected
import javax.inject.Inject

class ConnectivityHelper(private val context: Context) {
    /**
     * Function to check connectivity
     *
     * @return Will return True if connected, otherwise False
     */
    fun isNetConnected(): Boolean = context.isNetConnected()

    companion object {
        fun build(
            context: Context
        ): ConnectivityHelper = ConnectivityHelper(context)
    }
}