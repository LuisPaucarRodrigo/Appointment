package com.hybrid.appointment.data.system.notification

import com.hybrid.appointment.core.notification.AppNotificationManager
import com.hybrid.appointment.core.notification.Channel
import com.hybrid.appointment.core.notification.NotificationData
import com.hybrid.appointment.core.notification.NotificationHandler
import javax.inject.Inject

class AppointmentNotification @Inject constructor(
    private val appNotificationManager: AppNotificationManager
): NotificationHandler {

    companion object {
        val CHANNEL = Channel.APPOINTMENT
    }

    override fun canHandle(type: Channel): Boolean {
        return type == CHANNEL
    }

    override fun handle(data:NotificationData) {
        appNotificationManager.show(
            NotificationData(
                channel = CHANNEL,
                id = data.id,
                title = data.title,
                text = data.text
            )
        )
    }
}