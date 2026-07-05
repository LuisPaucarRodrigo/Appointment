package com.hybrid.appointment.core.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.hybrid.appointment.core.alarm.PropsIntent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AppReminderReceiver: BroadcastReceiver() {

    @Inject
    lateinit var handlers: Set<@JvmSuppressWildcards NotificationHandler>

    override fun onReceive(context: Context, intent: Intent) {
        val id = intent.getIntExtra(PropsIntent.ID.valueName,0)
        val channelId = intent.getStringExtra(PropsIntent.CHANNEL.valueName) ?: ""
        val title = intent.getStringExtra(PropsIntent.TITLE.valueName) ?: ""
        val extraMessage = intent.getStringExtra(PropsIntent.EXTRA_MESSAGE.valueName) ?: ""
        val channel = Channel.entries.firstOrNull{ it.valueId == channelId}

        val notification =  handlers.find { it.canHandle(requireNotNull(channel)) }
        notification?.handle(NotificationData(requireNotNull(channel),id,title,extraMessage))
    }
}