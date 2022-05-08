package  com.android.nearby.data.model

import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("address")
    val address: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("cross_street")
    val cross_street: String,
    @SerializedName("formatted_address")
    val formatted_address: String,
    @SerializedName("locality")
    val locality: String,
    @SerializedName("neighborhood")
    val neighbourhood: List<String>?,
    @SerializedName("postcode")
    val postcode: String,
    @SerializedName("region")
    val region: String,
)
