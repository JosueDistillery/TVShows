package com.distillery.tvshows.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = TVShow.TABLE_NAME)
data class TVShow(
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
    var isFavorite: Boolean = false,
) : Parcelable {

    companion object {

        /**
         * Table name to store favorites TV Shows
         */
        const val TABLE_NAME: String = "shows"

        /**
         * Build [TVShow] entity from [TVShow] model
         */
        fun from(tvShow: com.distillery.tvshows.data.model.TVShow): TVShow = TVShow(
            id = tvShow.id,
            name = tvShow.name,
            type = tvShow.type,
            language = tvShow.language,
            genres = tvShow.genres ?: emptyList(),
            premiered = tvShow.premiered,
            ended = tvShow.ended ?: "",
            officialSite = tvShow.officialSite ?: "",
            rating = tvShow.rating?.average?.toFloat() ?: 0.0f,
            network = tvShow.network?.name ?: "",
            imdb = tvShow.externals?.imdb ?: "",
            image = tvShow.image?.medium ?: "",
            summary = tvShow.summary,
        )
    }
}