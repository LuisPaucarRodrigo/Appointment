package com.hybrid.appointment.ui.screen.appointment.list

import com.hybrid.appointment.domain.appointment.Appointment

data class AppointmentListUiState(
    val appointments: List<Appointment> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)