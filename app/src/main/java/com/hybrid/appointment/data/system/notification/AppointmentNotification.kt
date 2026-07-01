package com.hybrid.appointment.data.system.notification

import android.app.NotificationManager
import com.hybrid.appointment.core.notification.AppNotificationManager
import com.hybrid.appointment.core.notification.Channel
import com.hybrid.appointment.core.notification.NotificationChannelConfig
import com.hybrid.appointment.core.notification.NotificationData
import javax.inject.Inject

class AppointmentNotification @Inject constructor(
    private val appNotificationManager: AppNotificationManager
) {
    companion object {
        val CHANNEL = Channel.APPOINTMENT
        const val IMPORTANCE = NotificationManager.IMPORTANCE_HIGH
    }

    operator fun invoke(appointmentId:Long, title: String, message: String) {

        appNotificationManager.createChannel(
            NotificationChannelConfig(
                channel = CHANNEL,
                importance = IMPORTANCE
            )
        )

        appNotificationManager.show(
            NotificationData(
                channel = CHANNEL,
                id = appointmentId.toInt(),
                title = title,
                text = message
            )
        )
    }
}