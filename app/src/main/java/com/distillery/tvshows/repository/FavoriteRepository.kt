package com.distillery.tvshows.repository

import android.util.Log
import com.distillery.tvshows.data.entity.Favorite
import com.distillery.tvshows.data.entity.TVShow
import com.distillery.tvshows.database.dao.FavoriteDao

class FavoriteRepository(
    private val favoriteDao: FavoriteDao
) {
    suspend fun getFavoriteById(tvShowId: Int): Favorite? = try {
        val favorite = favoriteDao.getFavoriteById(tvShowId)
        favorite
    } catch (e: Exception) {
        Log.e(TAG, "Something went wrong, ${e.localizedMessage}")
        null
    }

    suspend fun getFavorites(): List<Favorite> = try {
        val favorites = favoriteDao.getFavorites()
        favorites
    } catch (e: Exception) {
        Log.e(TAG, "Something went wrong, $e.localizedMessage")
        emptyList()
    }

    suspend fun getFavoriteTVShows(): List<TVShow> = try {
        val tvShows = favoriteDao.getFavoriteTVShows()
        tvShows
    } catch (e: Exception) {
        Log.e(TAG, "Something went wrong, $e.localizedMessage")
        emptyList()
    }

    suspend fun AddFavorite(favorite: Favorite): Boolean = try {
        favoriteDao.AddFavorite(favorite)
        true
    } catch (e: Exception) {
        Log.e(TAG, "Something went wrong, $e.localizedMessage")
        false
    }

    suspend fun RemoveFavorite(favorite: Favorite): Boolean = try {
        favoriteDao.RemoveFavorite(favorite)
        true
    } catch (e: Exception) {
        Log.e(TAG, "Something went wrong, $e.localizedMessage")
        false
    }

    companion object {
        val TAG: String = FavoriteRepository::class.java.simpleName

        /**
         * Factory function for [FavoriteRepository]
         */
        fun buildRepository(favoriteDao: FavoriteDao): FavoriteRepository =
            FavoriteRepository(favoriteDao)
    }
}