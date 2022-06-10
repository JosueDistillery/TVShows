package com.distillery.tvshows.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("medium")
    @Expose
    var medium : String,
    @SerializedName("original")
    @Expose
    var original : String
)