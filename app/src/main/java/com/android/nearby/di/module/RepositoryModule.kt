package com.android.nearby.di.module


import com.android.nearby.data.PlacesRepository
import com.android.nearby.data.Repository
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