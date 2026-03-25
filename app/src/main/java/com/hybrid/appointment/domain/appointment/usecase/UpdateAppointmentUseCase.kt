package com.hybrid.appointment.domain.appointment.usecase

import com.hybrid.appointment.domain.Result
import com.hybrid.appointment.domain.appointment.Appointment
import com.hybrid.appointment.domain.appointment.AppointmentRepositories
import javax.inject.Inject

class UpdateAppointmentUseCase @Inject constructor(
    private val appointmentRepositories: AppointmentRepositories
) {
    suspend operator fun invoke(appointment: Appointment): Result<Unit> {
        return appointmentRepositories.updateAppointment(appointment)
    }
}