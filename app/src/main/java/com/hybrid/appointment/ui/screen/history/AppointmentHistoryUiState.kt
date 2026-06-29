package com.hybrid.appointment.ui.screen.history

sealed class AppointmentHistoryEvent {
    data class Error(val errorMessage:String): AppointmentHistoryEvent()
}