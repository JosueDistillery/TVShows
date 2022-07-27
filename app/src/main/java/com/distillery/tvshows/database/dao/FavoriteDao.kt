package com.distillery.tvshows.database.dao

import androidx.room.*
import com.distillery.tvshows.data.entity.Favorite
import com.distillery.tvshows.data.entity.TVShow

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorites")
    suspend fun getFavorites(): List<Favorite>

    @Query("SELECT * FROM favorites WHERE tvShowId = :tvShowId")
    suspend fun getFavoriteById(tvShowId: Int): Favorite

    @Query("SELECT * FROM shows, favorites WHERE id = tvShowId")
    suspend fun getFavoriteTVShows(): List<TVShow>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun AddFavorite(favorite: Favorite)

    @Delete
    suspend fun RemoveFavorite(favorite: Favorite)
}