package com.distillery.tvshows.utils.helpers

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import javax.inject.Inject

class ConnectivityHelper(
    private val connectivityManager: ConnectivityManager
    ) {
    /**
     * Function to check connectivity
     *
     * @return Will return True if connected, otherwise False
     */
    fun isNetConnected(): Boolean {
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)
    }

    companion object {
        fun build(
            connectivityManager: ConnectivityManager
        ): ConnectivityHelper = ConnectivityHelper(connectivityManager)
    }
}