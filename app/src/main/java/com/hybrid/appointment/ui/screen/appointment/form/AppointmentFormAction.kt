package com.hybrid.appointment.ui.screen.appointment.form

sealed class AppointmentFormAction {
    data class TitleChanged(val title: String): AppointmentFormAction()
    data class DateChanged(val date: String): AppointmentFormAction()
    data class TimeChanged(val time: String): AppointmentFormAction()
    data class LocationChanged(val lat: Double,val lon: Double): AppointmentFormAction()
}