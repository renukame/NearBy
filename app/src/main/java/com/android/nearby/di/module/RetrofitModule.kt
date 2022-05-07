package com.adyen.android.assignment.di.module


import com.adyen.android.assignment.BuildConfig
import com.adyen.android.assignment.data.api.PlacesService
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Handles Retrofit related Injection
 */

@Module
class RetrofitModule {

    private val BASE_URL = "https://api.foursquare.com/v3/"

    @Provides
    @Singleton
    fun apiRequestInterceptor(): Interceptor {
        return Interceptor { chain ->
            val requestBuilder = chain.request().newBuilder().addHeader("Authorization", BuildConfig.API_KEY)
            val request = requestBuilder.build()
            chain.proceed(request)
        }

    }

    @Provides
    @Singleton
    fun provideOkHttpClient(apiRequestInterceptor: Interceptor): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(apiRequestInterceptor)
        return httpClient.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideTMDBApi(retrofit: Retrofit): PlacesService {
        return retrofit.create(PlacesService::class.java)
    }
}