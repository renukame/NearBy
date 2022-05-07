package com.adyen.android.assignment

import android.app.Application
import com.adyen.android.assignment.di.component.AppComponent
import com.adyen.android.assignment.di.component.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

/**
 * Initializing Dagger
 */

class PlacesApplication : Application(), HasAndroidInjector {
    private lateinit var appComponent: AppComponent

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().application(this).build()
        appComponent.inject(this)
    }

    override fun androidInjector() = androidInjector
}