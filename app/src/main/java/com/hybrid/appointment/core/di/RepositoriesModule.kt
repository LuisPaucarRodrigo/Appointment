package com.hybrid.appointment.core.di

import com.hybrid.appointment.data.local.repositoriesImpl.AppointmentRepositoriesImpl
import com.hybrid.appointment.data.local.repositoriesImpl.GeocodeRepositoryImpl
import com.hybrid.appointment.data.remoto.repositoriesImpl.GoogleMapsRepositoryImpl
import com.hybrid.appointment.data.system.LocationSettingsRepositoryImpl
import com.hybrid.appointment.domain.appointment.repositories.AppointmentRepositories
import com.hybrid.appointment.domain.appointment.repositories.LocationRepositories
import com.hybrid.appointment.domain.appointment.repositories.GoogleMapsRepositories
import com.hybrid.appointment.domain.appointment.repositories.LocationSettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {

    @Binds
    abstract fun bindGetAllAppointment(impl: AppointmentRepositoriesImpl): AppointmentRepositories

    @Binds
    abstract fun bindGetAddressFromLocation(impl: GeocodeRepositoryImpl) : LocationRepositories

    @Binds
    abstract fun bindGetRoute(impl: GoogleMapsRepositoryImpl): GoogleMapsRepositories

    @Binds
    abstract fun bindLocationSettings(impl: LocationSettingsRepositoryImpl): LocationSettingsRepository

}