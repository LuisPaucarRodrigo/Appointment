package com.hybrid.appointment.ui.screen.list

import com.hybrid.appointment.ui.screen.detail.AppointmentUi

data class AppointmentListUiState(
    val appointments: List<AppointmentUi> = emptyList(),
    val isLoading: Boolean = false,
)

sealed class AppointmentListEvent {
    data class Error(val errorMessage:String): AppointmentListEvent()
    data class Success(val successMessage:String): AppointmentListEvent()
}S