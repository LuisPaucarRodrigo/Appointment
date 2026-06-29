package com.hybrid.appointment.domain.appointment.usecase

import com.hybrid.appointment.domain.Result
import com.hybrid.appointment.domain.appointment.entities.Appointment
import com.hybrid.appointment.domain.appointment.repositories.AppointmentRepositories
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAppointmentByIdUseCase @Inject constructor(
    private val appointmentRepositories: AppointmentRepositories
) {
    operator fun invoke(id:Long): Flow<Result<Appointment>>{
        return appointmentRepositories.getAppointmentById(id).flowOn(Dispatchers.IO)
    }
}