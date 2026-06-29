package com.hybrid.appointment.domain.appointment.usecase

import com.hybrid.appointment.domain.Result
import com.hybrid.appointment.domain.appointment.entities.Appointment
import com.hybrid.appointment.domain.appointment.repositories.AppointmentRepositories
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAllAppointmentsUseCase @Inject constructor(
    private val appointmentRepositories: AppointmentRepositories
) {
    operator fun invoke(): Flow<Result<List<Appointment>>>{
        return appointmentRepositories.getAllAppointments().flowOn(Dispatchers.IO)
    }
}

