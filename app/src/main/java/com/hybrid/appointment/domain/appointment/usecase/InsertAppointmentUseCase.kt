package com.hybrid.appointment.domain.appointment.usecase

import com.hybrid.appointment.domain.Result
import com.hybrid.appointment.domain.appointment.repositories.AppointmentRepositories
import com.hybrid.appointment.domain.appointment.entities.Appointment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class InsertAppointmentUseCase @Inject constructor(
    private val appointmentRepositories: AppointmentRepositories
) {
    suspend operator fun invoke(appointment: Appointment): Result<Unit>{
        return withContext(Dispatchers.IO) {
            appointmentRepositories.insertAppointment(appointment)
        }
    }
}