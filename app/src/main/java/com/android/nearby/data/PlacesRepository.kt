package com.android.nearby.data

import android.location.Location
import com.android.nearby.data.api.PlacesService
import com.android.nearby.data.api.VenueRecommendationsQueryBuilder
import com.android.nearby.data.model.Places
import java.lang.Exception
import javax.inject.Inject

/**
 * Repository which handle
 * Handles fetching data from cache or Places API .
 */
class PlacesRepository @Inject constructor(
    private val placesService: PlacesService,
    private val queryBuilder: VenueRecommendationsQueryBuilder
) : Repository {

    private val cache = mutableMapOf<String, List<Places>?>()
    private lateinit var locationStr: String

    override suspend fun getAllNearByVenues(location: Location): APIResult<List<Places>> {
        locationStr = "${location.latitude},${location.longitude}"
        val cachedData = getCachedData(locationStr)
        return if (cachedData.isNotEmpty()) {
            APIResult.Success(cachedData)
        } else {
            fetchFromServer(location)
        }
    }

    private suspend fun fetchFromServer(location: Location): APIResult<List<Places>> {
        try {
            val query = queryBuilder
                .setLatitudeLongitude(location.latitude, location.longitude)
                .build()

            val response = placesService.getVenueRecommendations(query)
            if (response.isSuccessful) {
                val body = response.body()
                if (body?.results != null) {
                    addDataToCache(locationStr, body.results)
                    return APIResult.Success(body.results)
                }
            }
            return APIResult.Error("Error getting result ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return APIResult.Error("Exception getting result ${e.message}")
        }
    }

    private fun getCachedData(location: String): List<Places> {
        var cachedData = mutableListOf<Places>()
        if (cache.containsKey(location))
            cachedData = cache[location] as MutableList<Places>
        return cachedData
    }

    private fun addDataToCache(location: String, list: List<Places>?) {
        cache[location] = list
    }

}