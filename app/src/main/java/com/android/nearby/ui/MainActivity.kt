package com.adyen.android.assignment.ui

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.getSystemService
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.adyen.android.assignment.BuildConfig
import com.adyen.android.assignment.ui.viewmodel.MainViewModel
import com.adyen.android.assignment.utils.PermissionUtils
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.android.AndroidInjection
import javax.inject.Inject
import com.adyen.android.assignment.R
import com.adyen.android.assignment.databinding.ActivityMainBinding
import com.adyen.android.assignment.databinding.AlertDialogBinding
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.Task


/**
 * Main Entry point of Application
 * Handles permissions and Internet Connection
 */
class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    @Inject
    lateinit var permissionUtils: PermissionUtils

    private val REQUEST_PERMISSION_LOCATION = 0

    private var connectivityChecker: ConnectivityChecker? = null

    private val locationProviderClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(applicationContext)
    }

    private var cancellationTokenSource = CancellationTokenSource()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var mainViewModel: MainViewModel

    private lateinit var mBinding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setSupportActionBar(mBinding.toolbar)

        mainViewModel =
            ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        connectivityChecker()
        handleConnectivity()
        if (!isLocationPermissionGranted()) {
            requestPermission()
        }
    }

    private fun connectivityChecker() {
        val connectivityManager = getSystemService<ConnectivityManager>()
        if (connectivityManager != null) {
            connectivityChecker = ConnectivityChecker(connectivityManager)
        }
    }

    //Handling Network Connection
    private fun handleConnectivity() {
        connectivityChecker?.apply {
            lifecycle.addObserver(this)
            connectedStatus.observe(this@MainActivity, Observer<Boolean> {
                if (it) {
                    handleNetworkConnected()
                } else {
                    handleNoNetworkConnection()
                }
            })
        } ?: handleNoNetworkConnection()
    }

    //When internet is available requesting permission or getting current location
    private fun handleNetworkConnected() {
        Log.d(TAG, "handleNetworkConnected : ${mainViewModel.permissionState}")
        mBinding.noInternetImage.visibility = View.GONE
        mBinding.navHostFragment.visibility = View.VISIBLE
        if (permissionUtils.isLocationPermissionEnabled()) {
            getCurrentLocation()
        } else {
            if (!mainViewModel.permissionState) {
                requestLocationPermission()
            }
        }
    }

    //Handling when internet is not available
    private fun handleNoNetworkConnection() {
        mBinding.navHostFragment.visibility = View.GONE
        mBinding.noInternetImage.visibility = View.VISIBLE
    }

    //Display alert dialog with relevant info to make user aware location permission
    private fun showAlertDialog(
    ) {
        val alertLayoutBinding = AlertDialogBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(this, R.style.CustomAlertDialog).create()
        builder.setView(alertLayoutBinding.root)
        builder.setCanceledOnTouchOutside(false)
        builder.show()
        alertLayoutBinding.started.text = getString(R.string.open_settings)
        alertLayoutBinding.title.text = getString(R.string.setting_perm_info_text)
        alertLayoutBinding.message.text = getString(R.string.setting_perm_info_message)
        alertLayoutBinding.started.setOnClickListener {
            builder.dismiss()
            openAppSetting()
        }
        builder.setOnCancelListener {
            builder.dismiss()
            finish()
        }

    }

    //Requesting for permission, if permission granted Getting Current location using fusedLocationProvider
    private fun getCurrentLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                val currentLocationTask: Task<Location> = locationProviderClient.lastLocation

                currentLocationTask.addOnCompleteListener { task: Task<Location> ->
                    if (task.isSuccessful && task.result != null) {
                        val result: Location = task.result
                        Log.d(TAG, "CurrentLocation : ${result.latitude},${result.longitude}")
                        mainViewModel.refreshLocation(result)

                    } else {
                        val exception = task.exception
                        if (exception != null) {
                            exception.message?.let { Log.d(TAG, it) }
                        }
                        mainViewModel.refreshLocation(null)
                    }
                }
            }
        }
    }

    /**
     * Requesting Permission
     */
    private fun requestPermission() {
        if (mainViewModel.permissionState) {
            Log.d(TAG, "Requesting Permission")
            ActivityCompat.requestPermissions(
                this,
                permissionUtils.getLocationPermission(),
                REQUEST_PERMISSION_LOCATION
            )
        }
    }

    /**
     * Permission result handled here and
     * setting permission flag viewmodel
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.d(TAG, "onRequestPermissionsResult")
        mainViewModel.setPermissionState(false)
        if (requestCode == REQUEST_PERMISSION_LOCATION) {
            if (grantResults.isNotEmpty()) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // getting current Location
                    getCurrentLocation()
                } else {
                    // open settings to provide permission
                    if (!mainViewModel.permissionState) {
                        showAlertDialog()
                    }
                }
            }
        }
    }


    // If Permission is not enabled,requesting permission
    private fun requestLocationPermission() {
        Log.d(TAG, "requestLocationPermission")
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            // open settings to provide permission
            showAlertDialog()
        } else {
            // Requesting permission
            mainViewModel.setPermissionState(true)
            requestPermission()
        }
    }


    private fun isLocationPermissionGranted(): Boolean {
        return permissionUtils.isLocationPermissionEnabled()
    }

    //open Setting Screen to let user enable permission from settings
    private fun openAppSetting() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts(
            "package",
            BuildConfig.APPLICATION_ID,
            null
        )
        intent.data = uri
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun onStop() {
        super.onStop()
        cancellationTokenSource.cancel()
    }
}


