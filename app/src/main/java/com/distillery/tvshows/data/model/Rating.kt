package com.distillery.tvshows.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Rating(
    @SerializedName("average")
    @Expose
    val average : Double?= null,
)
