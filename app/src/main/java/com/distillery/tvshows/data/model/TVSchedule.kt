package com.distillery.tvshows.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TVSchedule(
    @SerializedName("time")
    @Expose
    var time : String,
    @SerializedName("days")
    @Expose
    var days : List<String>
)
