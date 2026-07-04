package com.hybrid.appointment.ui

import android.app.Application
import com.hybrid.appointment.core.notification.AppNotificationManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class AppointmentApplication: Application() {

    @Inject
    lateinit var appNotificationManager: AppNotificationManager

    override fun onCreate() {
        super.onCreate()
        appNotificationManager.createChannel()
    }
}