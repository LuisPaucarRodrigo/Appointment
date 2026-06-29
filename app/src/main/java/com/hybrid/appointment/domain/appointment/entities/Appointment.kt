package com.hybrid.appointment.domain.appointment.entities

data class Appointment(
    val id:Long,
    val title:String,
    val date:String,
    val time:String,
    val lat:Double,
    val lon:Double,
    val address:String,
    val state: AppointmentState
)

enum class AppointmentState {
    NO_VISITADO,
    EN_RUTA,
    VISITADO
}