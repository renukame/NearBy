package com.android.nearby.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.nearby.ui.viewmodel.MainViewModel
import com.android.nearby.ui.viewmodel.ViewModelFactory
import com.android.nearby.ui.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Handles ViewModel Injection
 */

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindPlacesViewModel(placesViewModel: MainViewModel): ViewModel
}