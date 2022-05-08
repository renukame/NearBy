package com.android.nearby.utils

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.os.Build
import javax.inject.Inject

/**
 * Utility class for handling permission
 */

class PermissionUtils @Inject constructor(private val application: Application) {

    fun getLocationPermission(): Array<String> {
        val perms: ArrayList<String> = ArrayList()
        perms.add(Manifest.permission.ACCESS_FINE_LOCATION)
        perms.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        return perms.toTypedArray()
    }


    fun isLocationPermissionEnabled(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            (application.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    && (application.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        } else {
            return true
        }
    }

}