package com.hybrid.appointment.data.local.repositoriesImpl

import com.hybrid.appointment.data.local.database.dao.AppointmentDao
import com.hybrid.appointment.data.local.database.entities.toAppointmentEntity
import com.hybrid.appointment.domain.appointment.Appointment
import com.hybrid.appointment.domain.appointment.AppointmentRepositories
import com.hybrid.appointment.domain.appointment.toAppointment
import com.hybrid.appointment.domain.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppointmentRepositoriesImpl @Inject constructor(
    private val appointmentDao: AppointmentDao
) : AppointmentRepositories {
    override fun getAllAppointments(): Flow<List<Appointment>> {
        val appointmentEntities =  appointmentDao.getAllAppointments()
        return appointmentEntities.map { it.map { it.toAppointment() } }
    }

    override fun getAppointmentById(id: Long): Flow<Appointment> {
        val appointmentEntity = appointmentDao.getAppointmentById(id)
        return appointmentEntity.map { it.toAppointment() }
    }

    override suspend fun insertAppointment(appointment: Appointment): Result<Unit> {
        return try {
            val body = appointment.toAppointmentEntity()
            appointmentDao.insertAppointment(body)
            Result.Success(Unit)
        } catch (e: Exception){
            Result.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun updateAppointment(appointment: Appointment): Result<Unit> {
        return try {
            val body = appointment.toAppointmentEntity()
            appointmentDao.updateAppointment(body)
            Result.Success(Unit)
        } catch (e: Exception){
            Result.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun deleteAppointment(id: Long): Result<Unit> {
        return try {
            appointmentDao.deleteAppointmentById(id)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e.message ?: "Unknown error")
        }
    }
}