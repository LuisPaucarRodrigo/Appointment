package com.hybrid.appointment.core.di

import com.hybrid.appointment.core.notification.NotificationHandler
import com.hybrid.appointment.data.system.notification.AppointmentNotification
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class NotificationHandlerModule {

    @Binds
    @IntoSet
    abstract fun bindAppointmentNotification(handler: AppointmentNotification): NotificationHandler

}