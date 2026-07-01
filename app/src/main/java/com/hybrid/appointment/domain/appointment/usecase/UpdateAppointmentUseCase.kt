package com.hybrid.appointment.domain.appointment.usecase

import com.hybrid.appointment.core.utils.Result
import com.hybrid.appointment.domain.appointment.entities.Appointment
import com.hybrid.appointment.domain.appointment.repositories.AppointmentRepositories
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateAppointmentUseCase @Inject constructor(
    private val appointmentRepositories: AppointmentRepositories
) {
    suspend operator fun invoke(appointment: Appointment): Result<Unit> {
        return withContext(Dispatchers.IO) {
            appointmentRepositories.updateAppointment(appointment)
        }
    }
}