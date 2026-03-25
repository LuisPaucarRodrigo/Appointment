package com.hybrid.appointment.domain.appointment

import com.hybrid.appointment.domain.Result
import kotlinx.coroutines.flow.Flow

interface AppointmentRepositories {
    fun getAllAppointments(): Flow<List<Appointment>>
    fun getAppointmentById(id:Long):Flow<Appointment>
    suspend fun insertAppointment(appointment: Appointment):Result<Unit>
    suspend fun updateAppointment(appointment: Appointment):Result<Unit>
    suspend fun deleteAppointment(id:Long):Result<Unit>
}