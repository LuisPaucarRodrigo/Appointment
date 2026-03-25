package com.hybrid.appointment.di

import com.hybrid.appointment.data.remoto.interceptor.AuthInterceptor
import com.hybrid.appointment.data.remoto.interceptor.ConfigInterceptor
import com.hybrid.appointment.data.remoto.routes.GoogleRoutesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
    private const val BASE_URL = "https://routes.googleapis.com/"

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor,configInterceptor: ConfigInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(configInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): GoogleRoutesApi {
        return retrofit.create(GoogleRoutesApi::class.java)
    }
}