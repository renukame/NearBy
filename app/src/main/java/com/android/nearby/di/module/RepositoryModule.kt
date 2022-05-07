package com.adyen.android.assignment.di.module

import com.adyen.android.assignment.data.PlacesRepository
import com.adyen.android.assignment.data.Repository
import dagger.Binds
import dagger.Module

/**
 * Handles Repository Injection
 */

@Module
abstract class RepositoryModule {
    @Binds
    abstract fun bindRepository(repositoryImpl: PlacesRepository): Repository
}