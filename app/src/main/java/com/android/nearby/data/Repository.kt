package com.adyen.android.assignment.data


import android.location.Location
import com.adyen.android.assignment.data.model.Places

interface Repository {

    suspend fun getAllNearByVenues(location: Location):APIResult<List<Places>>
}