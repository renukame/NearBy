package com.adyen.android.assignment.ui.viewmodel

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adyen.android.assignment.data.APIResult
import com.adyen.android.assignment.data.Repository
import com.adyen.android.assignment.data.model.Places
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * [ViewModel] responsible for providing data and  refreshing location
 */

class MainViewModel @Inject constructor(private val placesRepository: Repository) : ViewModel() {

    private val _placesMutableLiveData: MutableLiveData<APIResult<List<Places>>> = MutableLiveData()
    val placesLiveData: LiveData<APIResult<List<Places>>>
        get() = _placesMutableLiveData

    private val _loaderLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val loaderLiveData: LiveData<Boolean>
        get() = _loaderLiveData


    private var _permissionState: Boolean = true
    val permissionState: Boolean
        get() = _permissionState

    private fun getNearByVenues(location: Location) {
        _loaderLiveData.value = true

        viewModelScope.launch(Dispatchers.IO) {
            val resultList = placesRepository.getAllNearByVenues(location)
            _loaderLiveData.postValue(false)
            _placesMutableLiveData.postValue(resultList)
        }

    }

    fun refreshLocation(location: Location?) {
        if (location != null) {
            getNearByVenues(location)
        } else {
            _placesMutableLiveData.value =
                APIResult.Error("Please Enable Location, Location is null")
        }
    }

    //Setting permission flag
    fun setPermissionState(state: Boolean) {
        _permissionState = state
    }
}