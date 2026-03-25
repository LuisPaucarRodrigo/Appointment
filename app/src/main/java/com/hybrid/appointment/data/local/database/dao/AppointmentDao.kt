package com.hybrid.appointment.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.hybrid.appointment.data.local.database.entities.AppointmentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AppointmentDao {

    @Query("SELECT * FROM appointment_table  ORDER BY dateTime DESC")
    fun getAllAppointments(): Flow<List<AppointmentEntity>>

    @Insert
    suspend fun insertAppointment(appointmentEntity: AppointmentEntity)

    @Update
    suspend fun updateAppointment(appointmentEntity: AppointmentEntity)

    @Query("SELECT * FROM appointment_table WHERE id = :id")
    fun getAppointmentById(id:Long): Flow<AppointmentEntity>

    @Query("DELETE FROM appointment_table WHERE id = :id")
    suspend fun deleteAppointmentById(id:Long)
}