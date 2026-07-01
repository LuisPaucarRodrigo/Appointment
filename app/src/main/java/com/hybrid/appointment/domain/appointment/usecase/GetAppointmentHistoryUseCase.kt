package com.hybrid.appointment.domain.appointment.usecase

import androidx.paging.PagingData
import com.hybrid.appointment.core.utils.Result
import com.hybrid.appointment.domain.appointment.entities.Appointment
import com.hybrid.appointment.domain.appointment.repositories.AppointmentRepositories
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAppointmentHistoryUseCase @Inject constructor(
    private val appointmentRepositories: AppointmentRepositories
) {
    operator fun invoke(): Flow<Result<PagingData<Appointment>>>{
        return appointmentRepositories.getAppointmentHistory().flowOn(Dispatchers.IO)
    }
}