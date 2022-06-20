package com.distillery.tvshows.database.dao

import androidx.room.*
import com.distillery.tvshows.data.entity.FavoriteTVShow

@Dao
interface FavoriteTVShowDao {
    @Query("SELECT Count(id) FROM favorites WHERE id = :id LIMIT 1")
    suspend fun anyFavoriteById(id: Int): Int

    @Query("SELECT * FROM favorites WHERE id = :id")
    suspend fun getFavoriteById(id: Int): FavoriteTVShow

    @Query("SELECT * FROM favorites")
    suspend fun getFavorites(): List<FavoriteTVShow>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(tvShow: FavoriteTVShow)

    @Delete
    suspend fun removeFavorite(tvShow: FavoriteTVShow)
}