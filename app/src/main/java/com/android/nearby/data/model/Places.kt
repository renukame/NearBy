package com.adyen.android.assignment.data.model

import com.google.gson.annotations.SerializedName

data class Places(
    @SerializedName("fsq_id")
    val id: String,
    @SerializedName("categories")
    val categories: List<Category>?,
    @SerializedName("chains")
    val chains: List<Chain>?,
    @SerializedName("distance")
    val distance: Int,
    @SerializedName("geocodes")
    val geocode: GeoCode,
    @SerializedName("location")
    val location: Location,
    @SerializedName("name")
    val name: String,
    @SerializedName("timezone")
    val timezone: String,
)