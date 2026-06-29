package com.hybrid.appointment.domain.appointment.repositories

import androidx.paging.PagingData
import com.hybrid.appointment.domain.Result
import com.hybrid.appointment.domain.appointment.entities.Appointment
import kotlinx.coroutines.flow.Flow

interface AppointmentRepositories {
    fun getAllAppointments(): Flow<Result<List<Appointment>>>
    fun getAppointmentHistory(): Flow<Result<PagingData<Appointment>>>
    fun getAppointmentById(id:Long): Flow<Result<Appointment>>
    suspend fun insertAppointment(appointment: Appointment):Result<Unit>
    suspend fun updateAppointment(appointment: Appointment):Result<Unit>
    suspend fun deleteAppointment(id:Long):Result<Unit>
}