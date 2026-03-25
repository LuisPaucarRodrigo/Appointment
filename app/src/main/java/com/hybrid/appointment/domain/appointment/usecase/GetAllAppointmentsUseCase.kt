package com.hybrid.appointment.domain.appointment.usecase

import com.hybrid.appointment.domain.appointment.Appointment
import com.hybrid.appointment.domain.appointment.AppointmentRepositories
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllAppointmentsUseCase @Inject constructor(
    private val appointmentRepositories: AppointmentRepositories
) {
    operator fun invoke():Flow<List<Appointment>>{
        return appointmentRepositories.getAllAppointments()
    }
}

