package com.hybrid.appointment.core.notification

enum class Channel(val valueId:String,val valueName:String) {
    APPOINTMENT("appointment", "Notification of Appointment")
}

data class NotificationChannelConfig(
    val channel: Channel,
    val importance: Int
)

data class NotificationData(
    val channel: Channel,
    val id: Int,
    val title: String,
    val text: String
)