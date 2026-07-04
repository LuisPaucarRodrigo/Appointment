package com.hybrid.appointment.core.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.hybrid.appointment.core.notification.AppReminderReceiver
import com.hybrid.appointment.core.notification.Channel
import javax.inject.Inject

class AppAlarmManager @Inject constructor (
    private val context: Context
){
    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    fun schedule(item: AlarmItem) {
        val pendingIntent = createPendingIntent(item.id, item.title, item.message, item.type)
        alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, item.timeTrigger, pendingIntent)
    }

    fun cancel(appointmentId: Int) {
        val pendingIntent = createPendingIntent(appointmentId)
        alarmManager.cancel(pendingIntent)
        pendingIntent.cancel()
    }

    private fun createPendingIntent(id:Int, title:String? = null, message:String? = null, channel:Channel? = null): PendingIntent {
        val intent = Intent(context, AppReminderReceiver::class.java)
        intent.apply {

            putExtra(PropsIntent.ID.valueName, id)

            channel?.let {
                putExtra(PropsIntent.CHANNEL.valueName, it.valueId)
            }

            title?.let {
                putExtra(
                    PropsIntent.TITLE.valueName,
                    it
                )
            }

            message?.let {
                putExtra(
                    PropsIntent.EXTRA_MESSAGE.valueName,
                    it
                )
            }

        }

        return PendingIntent.getBroadcast(
            context,
            id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or
                    PendingIntent.FLAG_IMMUTABLE
        )
    }
}