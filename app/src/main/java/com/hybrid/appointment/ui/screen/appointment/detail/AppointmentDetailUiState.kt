package com.hybrid.appointment.ui.screen.appointment.detail

import com.google.android.gms.common.api.ResolvableApiException
import com.hybrid.appointment.domain.appointment.Appointment
import com.hybrid.appointment.domain.appointment.AppointmentState

data class AppointmentDetailUiState(
    val appointment: Appointment? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)

data class ShowError(
    val message: String? = null,
    val isShow: Boolean = false
)

data class ResolveGps(val resolvable: ResolvableApiException)