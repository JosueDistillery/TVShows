package com.distillery.tvshows.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Externals(
    @SerializedName("tvrage")
    @Expose
    var tvrage: Int,
    @SerializedName("thetvdb")
    @Expose
    var thetvdb: Int,
    @SerializedName("imdb")
    @Expose
    var imdb: String,
)
