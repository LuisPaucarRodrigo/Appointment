package com.hybrid.appointment.domain.appointment.usecase

import com.hybrid.appointment.domain.Result
import com.hybrid.appointment.domain.appointment.AppointmentRepositories
import javax.inject.Inject

class DeleteAppointmentUseCase @Inject constructor(
    private val appointmentRepositories: AppointmentRepositories
) {
    suspend operator fun invoke(id:Long): Result<Unit>{
        return appointmentRepositories.deleteAppointment(id)
    }
}