package com.android.nearby.di.component

import android.app.Application
import com.android.nearby.PlacesApplication
import com.android.nearby.di.module.*

import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [RetrofitModule::class, ActivityModule::class, FragmentModule::class,
        ViewModelModule::class, RepositoryModule::class, AndroidSupportInjectionModule::class]
)
interface AppComponent {
    fun inject(app: PlacesApplication)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: Application): Builder
        fun build(): AppComponent
    }

}