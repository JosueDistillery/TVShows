package com.distillery.tvshows.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Link(
    @SerializedName("href")
    @Expose
    var href: String,
)