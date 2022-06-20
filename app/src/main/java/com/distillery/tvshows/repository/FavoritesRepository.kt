package com.distillery.tvshows.repository

import com.distillery.tvshows.data.entity.FavoriteTVShow
import com.distillery.tvshows.data.model.TVShow
import com.distillery.tvshows.database.dao.FavoriteTVShowDao

class FavoritesRepository(
    private val favoriteTVShowDao: FavoriteTVShowDao
) {
    suspend fun anyFavoriteById(id: Int): Boolean = try {
        favoriteTVShowDao.anyFavoriteById(id) == 1
    } catch (error: Throwable) {
        false
    }

    suspend fun getFavorites(): List<FavoriteTVShow> = try {
        favoriteTVShowDao.getFavorites()
    } catch (error: Throwable) {
        emptyList()
    }

    suspend fun getFavoriteById(id: Int): FavoriteTVShow? = try {
        favoriteTVShowDao.getFavoriteById(id)
    } catch (error: Throwable) {
        null
    }

    suspend fun addFavoriteTVShow(favoriteTVShow: FavoriteTVShow): Boolean = try {
        favoriteTVShowDao.addFavorite(favoriteTVShow)
        true
    } catch (error: Throwable) {
        false
    }

    suspend fun removeFavoriteTVShow(favoriteTVShow: FavoriteTVShow): Boolean = try {
        favoriteTVShowDao.removeFavorite(favoriteTVShow)
        true
    } catch (error: Throwable) {
        false
    }

    companion object {
        /**
         * Factory function for [FavoritesRepository]
         */
        fun buildRepository(favoriteTVShowDao: FavoriteTVShowDao): FavoritesRepository =
            FavoritesRepository(favoriteTVShowDao)
    }
}