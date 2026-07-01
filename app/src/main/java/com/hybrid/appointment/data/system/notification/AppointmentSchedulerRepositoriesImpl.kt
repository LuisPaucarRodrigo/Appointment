package com.hybrid.appointment.data.system.notification

import com.hybrid.appointment.core.alarm.AppAlarmManager
import com.hybrid.appointment.core.alarm.toAlarm
import com.hybrid.appointment.core.utils.Result
import com.hybrid.appointment.domain.appointment.entities.Appointment
import com.hybrid.appointment.domain.appointment.repositories.AppointmentSchedulerRepositories
import javax.inject.Inject

class AppointmentSchedulerRepositoriesImpl @Inject constructor(
    private val appAlarmManager: AppAlarmManager
) : AppointmentSchedulerRepositories {
    override fun schedule(appointment: Appointment): Result<Unit> {
        return try {
            appAlarmManager.schedule(appointment.toAlarm())
            Result.Success(Unit)
        } catch (e: Exception){
            Result.Error(e.message ?: "Unknow error")
        }
    }

    override fun cancel(id: Long): Result<Unit> {
        return try {
            appAlarmManager.cancel(id.toInt())
            Result.Success(Unit)
        } catch (e: Exception){
            Result.Error(e.message ?: "Unknow error")
        }
    }
}