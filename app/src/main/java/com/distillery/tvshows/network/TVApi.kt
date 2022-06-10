package com.distillery.tvshows.network

import com.distillery.tvshows.data.model.TVShow
import retrofit2.Retrofit
import retrofit2.http.GET

/**
 * Service to make API calls for TV data
 */
interface TVApi {
    /**
     * Get TV Shows
     */
    @GET("/shows")
    suspend fun getShows(): List<TVShow>

    companion object {
        /**
         * Factory function for [TVApi]
         */
        fun create(retroFit: Retrofit): TVApi = retroFit.create(TVApi::class.java)
    }
}