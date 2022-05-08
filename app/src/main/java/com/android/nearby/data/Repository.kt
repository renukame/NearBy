package com.android.nearby.data


import android.location.Location
import com.android.nearby.data.model.Places


interface Repository {

    suspend fun getAllNearByVenues(location: Location):APIResult<List<Places>>
}