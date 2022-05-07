package com.adyen.android.assignment.data.api


import com.adyen.android.assignment.data.model.ResponseWrapper
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap


interface PlacesService {
    /**
     * Get venue recommendations.
     *
     * See [the docs](https://developer.foursquare.com/reference/places-nearby)
     */
    @GET("places/nearby")
    suspend fun getVenueRecommendations(@QueryMap query: Map<String, String>): Response<ResponseWrapper>


}
