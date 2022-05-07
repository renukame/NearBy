package com.adyen.android.assignment.data.api

import javax.inject.Inject

class VenueRecommendationsQueryBuilder @Inject constructor(): PlacesQueryBuilder() {
    private var latitudeLongitude: String? = null

    fun setLatitudeLongitude(latitude: Double, longitude: Double): VenueRecommendationsQueryBuilder {
        this.latitudeLongitude = "$latitude,$longitude"
        return this
    }

    override fun putQueryParams(queryParams: MutableMap<String, String>) {
        latitudeLongitude?.apply {
            queryParams["ll"] = this
            queryParams["limit"] = "30"
        }
    }
}
