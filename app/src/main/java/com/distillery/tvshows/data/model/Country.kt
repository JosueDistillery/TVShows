package com.distillery.tvshows.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("name")
    @Expose
    var name: String,
    @SerializedName("code")
    @Expose
    var code: String,
    @SerializedName("timezone")
    @Expose
    var timezone: String,
)
