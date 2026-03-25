package com.hybrid.appointment.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hybrid.appointment.domain.appointment.Appointment
import com.hybrid.appointment.domain.appointment.AppointmentState
import com.hybrid.appointment.domain.extensions.toDateTime

@Entity(tableName = "appointment_table")
data class AppointmentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val dateTime: Long,
    val lat: Double,
    val lon:Double,
    val address: String,
    val state: AppointmentState
)

fun Appointment.toAppointmentEntity():AppointmentEntity{
    return AppointmentEntity(
        id = id,
        title = title,
        dateTime = date.toDateTime(time),
        lat = lat,
        lon = lon,
        address = address,
        state = state
    )
}