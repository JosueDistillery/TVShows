package com.distillery.tvshows.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Links(
    @SerializedName("self")
    @Expose
    var self: Link?= null,
    @SerializedName("previousepisode")
    @Expose
    var previousepisode: Link?= null,
    @SerializedName("nextepisode")
    @Expose
    var nextepisode: Link?= null,
)