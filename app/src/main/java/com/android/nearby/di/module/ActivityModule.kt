package com.adyen.android.assignment.di.module

import com.adyen.android.assignment.ui.MainActivity
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