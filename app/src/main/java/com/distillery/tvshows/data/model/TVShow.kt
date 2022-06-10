package com.distillery.tvshows.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TVShow(
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("url")
    @Expose
    val url: String,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("type")
    @Expose
    val type: String,
    @SerializedName("language")
    @Expose
    val language: String,
    @SerializedName("genres")
    @Expose
    val genres: List<String>?= null,
    @SerializedName("status")
    @Expose
    val status: String,
    @SerializedName("runtime")
    @Expose
    val runtime: Int?= null,
    @SerializedName("averageRuntime")
    @Expose
    val averageRuntime: Int,
    @SerializedName("premiered")
    @Expose
    val premiered: String,
    @SerializedName("ended")
    @Expose
    val ended: String,
    @SerializedName("officialSite")
    @Expose
    val officialSite: String,
    @SerializedName("schedule")
    @Expose
    val schedule: TVSchedule?= null,
    @SerializedName("rating")
    @Expose
    val rating: Rating?= null,
    @SerializedName("weight")
    @Expose
    val weight: Int,
    @SerializedName("network")
    @Expose
    val network: TVNetwork?= null,
    @SerializedName("webChannel")
    @Expose
    val webChannel: TVNetwork?= null,
    @SerializedName("dvdCountry")
    @Expose
    val dvdCountry: Country?= null,
    @SerializedName("externals")
    @Expose
    val externals: Externals?= null,
    @SerializedName("image")
    @Expose
    val image: Image?= null,
    @SerializedName("summary")
    @Expose
    val summary: String,
    @SerializedName("updated")
    @Expose
    val updated: Int,
    @SerializedName("_links")
    @Expose
    val _links: Links?= null,
)