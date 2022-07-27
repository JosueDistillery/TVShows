package com.distillery.tvshows.repository

import android.util.Log
import com.distillery.tvshows.data.entity.TVShow
import com.distillery.tvshows.database.dao.FavoriteDao

class FavoriteRepository(
    private val favoriteDao: FavoriteDao
) {
    suspend fun anyFavoriteById(id: Int): Boolean = try {
        favoriteDao.anyFavoriteById(id) == 1
    } catch (e: Exception) {
        Log.e(TAG, "Something went wrong, $e")
        false
    }

    suspend fun getFavorites(): List<TVShow> = try {
        favoriteDao.getFavorites()
    } catch (e: Exception) {
        Log.e(TAG, "Something went wrong, $e")
        emptyList()
    }

    suspend fun getFavoriteById(id: Int): TVShow? = try {
        favoriteDao.getFavoriteById(id)
    } catch (e: Exception) {
        Log.e(TAG, "Something went wrong, $e")
        null
    }

    suspend fun addFavorite(favorite: TVShow): Boolean = try {
        favoriteDao.addFavorite(favorite)
        true
    } catch (e: Exception) {
        Log.e(TAG, "Something went wrong, $e")
        false
    }

    suspend fun removeFavorite(favorite: TVShow): Boolean = try {
        favoriteDao.removeFavorite(favorite)
        true
    } catch (e: Exception) {
        Log.e(TAG, "Something went wrong, $e")
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