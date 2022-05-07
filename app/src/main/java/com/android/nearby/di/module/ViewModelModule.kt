package com.adyen.android.assignment.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adyen.android.assignment.ui.viewmodel.MainViewModel
import com.adyen.android.assignment.ui.viewmodel.ViewModelFactory
import com.adyen.android.assignment.ui.viewmodel.ViewModelKey
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