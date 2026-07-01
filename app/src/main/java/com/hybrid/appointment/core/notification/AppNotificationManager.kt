package com.hybrid.appointment.core.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.hybrid.appointment.R
import javax.inject.Inject

class AppNotificationManager @Inject constructor(
    private val context: Context
) {
    val manager = context.getSystemService(NotificationManager::class.java)

    fun createChannel(data: NotificationChannelConfig){
        val channel = NotificationChannel(
            data.channel.valueId,
            data.channel.valueName,
            data.importance
        )
        manager.createNotificationChannel(channel)
    }

    fun show(data: NotificationData){
        val notification = NotificationCompat.Builder(context, data.channel.valueId)
                .setSmallIcon(R.drawable.outline_globe_location_pin_24)
                .setContentTitle(data.title)
                .setContentText(data.text)
                .setAutoCancel(true)
                .build()

        manager.notify(data.id, notification)
    }
}