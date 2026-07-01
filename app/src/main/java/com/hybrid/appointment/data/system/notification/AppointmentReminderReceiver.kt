package com.hybrid.appointment.data.system.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.hybrid.appointment.core.alarm.PropsIntent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AppointmentReminderReceiver: BroadcastReceiver() {

    @Inject
    lateinit var appointmentNotification: AppointmentNotification

    override fun onReceive(context: Context, intent: Intent) {
        val id = intent.getLongExtra(PropsIntent.ID.valueName,0L)
        val title = intent.getStringExtra(PropsIntent.TITLE.valueName) ?: ""
        val extraMessage = intent.getStringExtra(PropsIntent.EXTRAMESSAGE.valueName) ?: ""
        appointmentNotification(id,title,extraMessage)
    }
}