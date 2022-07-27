package com.distillery.tvshows.database.dao

import androidx.room.*
import com.distillery.tvshows.data.entity.TVShow

@Dao
interface FavoriteDao {
    @Query("SELECT Count(id) FROM shows WHERE id = :id LIMIT 1")
    suspend fun anyFavoriteById(id: Int): Int

    @Query("SELECT * FROM shows WHERE id = :id")
    suspend fun getFavoriteById(id: Int): TVShow

    @Query("SELECT * FROM shows WHERE isFavorite = 1")
    suspend fun getFavorites(): List<TVShow>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(tvShow: TVShow)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun removeFavorite(tvShow: TVShow)
}