package com.android.nearby.di.module


import com.android.nearby.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Handles Activity Injection
 */

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

}