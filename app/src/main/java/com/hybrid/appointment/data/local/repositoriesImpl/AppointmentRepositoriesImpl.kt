package com.hybrid.appointment.data.local.repositoriesImpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.hybrid.appointment.data.local.database.dao.AppointmentDao
import com.hybrid.appointment.data.local.database.entities.toDomain
import com.hybrid.appointment.data.local.database.entities.toEntity
import com.hybrid.appointment.domain.appointment.entities.Appointment
import com.hybrid.appointment.domain.appointment.repositories.AppointmentRepositories
import com.hybrid.appointment.domain.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppointmentRepositoriesImpl @Inject constructor(
    private val appointmentDao: AppointmentDao
) : AppointmentRepositories {
    override fun getAllAppointments(): Flow<Result<List<Appointment>>> {
        val appointmentEntities =  appointmentDao.getAppointmentsNotVisited()
        return appointmentEntities
            .catch { e -> Result.Error(e.message ?: "Unknow error") }
            .map { Result.Success(it.map { e-> e.toDomain() }) }
    }

    override fun getAppointmentHistory(): Flow<Result<PagingData<Appointment>>> {
        val appointmentHistory = appointmentDao.getAppointmentsHistory()
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { appointmentHistory }
        ).flow
            .catch { e-> Result.Error(e.message ?: "Unknow error") }
            .map { Result.Success(it.map { e -> e.toDomain()}) }
    }

    override fun getAppointmentById(id: Long): Flow<Result<Appointment>> {
        val appointmentEntity = appointmentDao.getAppointmentById(id)
        return appointmentEntity
            .catch { e -> Result.Error(e.message ?: "Unknow error") }
            .map { Result.Success(it.toDomain()) }
    }

    override suspend fun insertAppointment(appointment: Appointment): Result<Unit> {
        return try {
            val body = appointment.toEntity()
            appointmentDao.insertAppointment(body)
            Result.Success(Unit)
        } catch (e: Exception){
            Result.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun updateAppointment(appointment: Appointment): Result<Unit> {
        return try {
            val body = appointment.toEntity()
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