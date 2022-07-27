package com.distillery.tvshows.repository

import android.util.Log
import com.distillery.tvshows.data.entity.TVShow
import com.distillery.tvshows.database.dao.TVShowDao
import com.distillery.tvshows.network.TVApi
import com.distillery.tvshows.utils.helpers.ConnectivityHelper

class TVShowRepository (
    private val tvApi: TVApi,
    private val tvShowDao: TVShowDao,
    private val connectivity: ConnectivityHelper
) {
    suspend fun getTVShows(): List<TVShow> = try {
        if (connectivity.isNetConnected()) {
            val response = getTVShowsFromAPI()
            if (response.isNotEmpty()) {
                val tvShows = response.map { TVShow.from(it) }
                tvShowDao.addTVShows(tvShows)
                tvShows
            } else {
                Log.e(TAG, "Something went wrong")
                emptyList()
            }
        } else {
            getTVShowsFromDatabase()
        }
    } catch (e: Exception) {
        Log.e(TAG, "Something went wrong, $e.localizedMessage")
        emptyList()
    }

    private suspend fun getTVShowsFromAPI() = tvApi.getShows()

    private suspend fun getTVShowsFromDatabase() = tvShowDao.getShows()

    companion object {
        val TAG: String = TVShowRepository::class.java.simpleName

        /**
         * Factory function for [TVShowRepository]
         */
        fun buildRepository(
            tvApi: TVApi,
            tvShowDao: TVShowDao,
            connectivity: ConnectivityHelper
        ): TVShowRepository = TVShowRepository(tvApi, tvShowDao, connectivity)
    }
}