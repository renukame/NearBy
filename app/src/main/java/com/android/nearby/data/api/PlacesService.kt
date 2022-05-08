package com.android.nearby.data.api



import com.android.nearby.data.model.ResponseWrapper
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
