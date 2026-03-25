package com.hybrid.appointment.domain.appointment

import com.hybrid.appointment.data.local.database.entities.AppointmentEntity
import com.hybrid.appointment.domain.extensions.toDate
import com.hybrid.appointment.domain.extensions.toTime
import com.hybrid.appointment.ui.screen.appointment.form.AppointmentForm

data class Appointment(
    val id:Long,
    val title:String,
    val date:String,
    val time:String,
    val lat:Double,
    val lon:Double,
    val address:String,
    val state:AppointmentState
)

fun AppointmentEntity.toAppointment(): Appointment{
    return Appointment(
        id = id,
        title = title,
        date = dateTime.toDate(),
        time = dateTime.toTime(),
        lat = lat,
        lon = lon,
        address = address,
        state = state
    )
}

fun AppointmentForm.toAppointment():Appointment{
    return Appointment(
        id = id,
        title = title,
        date = date,
        time = time,
        lat = lat,
        lon = lon,
        address = address,
        state = state
    )
}