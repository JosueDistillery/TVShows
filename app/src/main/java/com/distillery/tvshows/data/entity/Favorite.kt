package com.distillery.tvshows.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = Favorite.TABLE_NAME)
data class Favorite (
    @PrimaryKey
    val favoriteId: String = UUID.randomUUID().toString(),
    val tvShowId: Int
) : Parcelable {

    companion object {

        /**
         * Table name to store favorites TV Shows
         */
        const val TABLE_NAME: String = "favorites"

        fun from(tvShow: TVShow): Favorite = Favorite(tvShowId = tvShow.id)
    }
}