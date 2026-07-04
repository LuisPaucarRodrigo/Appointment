package com.hybrid.appointment.core.alarm

import com.hybrid.appointment.core.notification.Channel
import com.hybrid.appointment.core.utils.toTriggerAlarm
import com.hybrid.appointment.domain.appointment.entities.Appointment

data class AlarmItem(
    val type: Channel,
    val id:Int,
    val time:String,
    val title: String,
    val message: String,
    val timeTrigger:Long
)

fun Appointment.toAlarm(): AlarmItem{
    return AlarmItem(
        type = Channel.APPOINTMENT,
        id = id.toInt(),
        time = time,
        title = "Cita con $title",
        message = "Tu cita está programada para hoy a las $time en $address.",
        timeTrigger = date.toTriggerAlarm(time)
    )
}

enum class PropsIntent(val valueName:String){
    ID("id"),
    CHANNEL("channel"),
    TITLE("title"),
    EXTRA_MESSAGE("extra_message")
}