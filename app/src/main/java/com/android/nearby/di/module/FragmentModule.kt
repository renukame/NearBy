package com.adyen.android.assignment.di.module

import com.adyen.android.assignment.ui.fragments.PlacesListFragment
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