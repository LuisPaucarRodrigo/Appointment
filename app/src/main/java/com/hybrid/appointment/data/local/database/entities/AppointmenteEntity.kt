package com.hybrid.appointment.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hybrid.appointment.domain.appointment.entities.Appointment
import com.hybrid.appointment.domain.appointment.entities.AppointmentState
import com.hybrid.appointment.core.utils.toDate
import com.hybrid.appointment.core.utils.toDateTime
import com.hybrid.appointment.core.utils.toTime

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

fun Appointment.toEntity():AppointmentEntity{
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

fun AppointmentEntity.toDomain(): Appointment{
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