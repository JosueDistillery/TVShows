package com.distillery.tvshows.database.dao

import androidx.room.*
import com.distillery.tvshows.data.entity.TVShow

@Dao
interface TVShowDao {
    @Query("SELECT * FROM shows")
    suspend fun getShows(): List<TVShow>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTVShows(tvShows: List<TVShow>)
}