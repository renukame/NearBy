package com.adyen.android.assignment.data.model

import com.google.gson.annotations.SerializedName

data class ResponseWrapper(
    @SerializedName("results")
    val results: List<Places>?,
)
