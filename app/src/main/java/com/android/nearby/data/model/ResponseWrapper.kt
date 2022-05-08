package com.android.nearby.data.model

import com.google.gson.annotations.SerializedName

data class ResponseWrapper(
    @SerializedName("results")
    val results: List<Places>?,
)
