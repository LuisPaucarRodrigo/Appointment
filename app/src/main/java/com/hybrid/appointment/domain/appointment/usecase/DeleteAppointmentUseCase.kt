package com.hybrid.appointment.domain.appointment.usecase

import com.hybrid.appointment.core.utils.Result
import com.hybrid.appointment.domain.appointment.repositories.AppointmentRepositories
import com.hybrid.appointment.domain.appointment.repositories.AppointmentSchedulerRepositories
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteAppointmentUseCase @Inject constructor(
    private val appointmentRepositories: AppointmentRepositories,
    private val appointmentSchedulerRepositories: AppointmentSchedulerRepositories
) {
    suspend operator fun invoke(id:Long): Result<Unit>{
        return withContext(Dispatchers.IO) {
            appointmentRepositories.deleteAppointment(id)
            appointmentSchedulerRepositories.cancel(id)
        }
    }
}