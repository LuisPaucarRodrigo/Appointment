package com.hybrid.appointment.core.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.hybrid.appointment.data.system.notification.AppointmentReminderReceiver
import javax.inject.Inject

class AppAlarmManager @Inject constructor (
    private val context: Context
){
    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    fun schedule(item: AlarmItem) {
        val pendingIntent = createPendingIntent(item.id, item.title, item.message)
        alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, item.timeTrigger, pendingIntent)
    }

    fun cancel(appointmentId: Int) {
        val pendingIntent = createPendingIntent(appointmentId)
        alarmManager.cancel(pendingIntent)
        pendingIntent.cancel()
    }

    private fun createPendingIntent(id: Int, title: String? = null, extraMessage: String? = null): PendingIntent {

        val intent = Intent(context, AppointmentReminderReceiver::class.java)
        intent.putExtra(PropsIntent.ID.valueName,id)

        title?.let {
            intent.putExtra(PropsIntent.TITLE.valueName, it)
        }

        extraMessage?.let {
            intent.putExtra(PropsIntent.EXTRAMESSAGE.valueName, it)
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