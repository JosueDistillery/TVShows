package com.distillery.tvshows.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.distillery.tvshows.data.model.*
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = FavoriteTVShow.TABLE_NAME)
data class FavoriteTVShow(
    @PrimaryKey
    val id: Int,
    val name: String,
    val type: String,
    val language: String,
    val genres: List<String>,
    val premiered: String,
    val ended: String,
    val officialSite: String,
    val rating: Float,
    val network: String,
    var imdb: String,
    val image: String,
    val summary: String,
) : Parcelable {

    companion object {

        /**
         * Table name to store favorites TV Shows
         */
        const val TABLE_NAME: String = "favorites"

        /**
         * Build [FavoriteTVShow] from [TVShow]
         */
        fun from(tvShow: TVShow): FavoriteTVShow = FavoriteTVShow(
            id = tvShow.id,
            name = tvShow.name,
            type = tvShow.type,
            language = tvShow.language,
            genres = tvShow.genres!!,
            premiered = tvShow.premiered,
            ended = tvShow.ended,
            officialSite = tvShow.officialSite,
            rating = tvShow.rating!!.average!!.toFloat(),
            network = tvShow.network!!.name,
            imdb = tvShow.externals!!.imdb,
            image = tvShow.image!!.medium,
            summary = tvShow.summary,
        )
    }
}