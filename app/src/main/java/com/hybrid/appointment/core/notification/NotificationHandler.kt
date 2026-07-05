package com.hybrid.appointment.core.notification

interface NotificationHandler {
    fun canHandle(type: Channel): Boolean
    fun handle(data:NotificationData)
}