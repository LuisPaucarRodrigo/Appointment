package com.hybrid.appointment.ui.screen.appointment.form

data class AppointmentFormUiState(
    val appointment: AppointmentForm = AppointmentForm(),
    val appointmentError: AppointmentFormError = AppointmentFormError(),
    val isLoading: Boolean = false,
    val errorMessage:String? = null
)