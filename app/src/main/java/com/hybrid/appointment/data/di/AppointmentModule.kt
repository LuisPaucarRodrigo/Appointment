package com.hybrid.appointment.data.di

import com.hybrid.appointment.data.remoto.services.GoogleRoutesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppointmentModule {

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): GoogleRoutesApi {
        return retrofit.create(GoogleRoutesApi::class.java)
    }

}