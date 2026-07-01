package com.hybrid.appointment.domain.appointment.repositories

import com.hybrid.appointment.domain.appointment.entities.Appointment
import com.hybrid.appointment.core.utils.Result
interface AppointmentSchedulerRepositories {

    fun schedule(appointment: Appointment): Result<Unit>

    fun cancel(id: Long): Result<Unit>

}