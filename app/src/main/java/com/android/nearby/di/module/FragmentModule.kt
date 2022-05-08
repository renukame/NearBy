package com.android.nearby.di.module

import com.android.nearby.ui.fragments.PlacesListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Handles Fragment Injection
 */

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributePlacesListFragment(): PlacesListFragment
}