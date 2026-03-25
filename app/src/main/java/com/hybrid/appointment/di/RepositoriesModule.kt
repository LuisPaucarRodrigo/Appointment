package com.hybrid.appointment.di

import com.hybrid.appointment.data.local.repositoriesImpl.AppointmentRepositoriesImpl
import com.hybrid.appointment.data.local.repositoriesImpl.GeocodeRepositoryImpl
import com.hybrid.appointment.data.remoto.repositoriesImpl.RouteRepositoryImpl
import com.hybrid.appointment.domain.appointment.AppointmentRepositories
import com.hybrid.appointment.domain.appointment.GeocodeRepositories
import com.hybrid.appointment.domain.appointment.RouteRepositories
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
    abstract fun bindGetAddressFromLocation(impl: GeocodeRepositoryImpl) : GeocodeRepositories

    @Binds
    abstract fun bindGetRoute(impl: RouteRepositoryImpl): RouteRepositories
}