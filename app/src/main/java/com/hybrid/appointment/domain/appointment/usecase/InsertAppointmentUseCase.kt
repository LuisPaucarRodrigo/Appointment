package com.hybrid.appointment.domain.appointment.usecase

import com.hybrid.appointment.domain.Result
import com.hybrid.appointment.domain.appointment.Appointment
import com.hybrid.appointment.domain.appointment.AppointmentRepositories
import com.hybrid.appointment.domain.appointment.toAppointment
import com.hybrid.appointment.ui.screen.appointment.form.AppointmentForm
import javax.inject.Inject

class InsertAppointmentUseCase @Inject constructor(
    private val appointmentRepositories: AppointmentRepositories
) {
    suspend operator fun invoke(appointment: AppointmentForm): Result<Unit>{
        return appointmentRepositories.insertAppointment(appointment.toAppointment())
    }
}