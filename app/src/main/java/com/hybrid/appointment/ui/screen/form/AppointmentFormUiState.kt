package com.hybrid.appointment.ui.screen.form

import com.hybrid.appointment.domain.appointment.entities.AppointmentState
import com.hybrid.appointment.domain.appointment.entities.Appointment

data class AppointmentFormUiState(
    val appointment: AppointmentForm = AppointmentForm(),
    val appointmentError: AppointmentFormError = AppointmentFormError(),
    val isLoading: Boolean = false,
)

data class AppointmentForm(
    val id:Long = 0,
    val title:String = "",
    val date:String = "",
    val time:String = "",
    val lat:Double = 0.0,
    val lon:Double = 0.0,
    val address:String = "",
    val state:AppointmentState = AppointmentState.NO_VISITADO
)

data class AppointmentFormError(
    val titleError:Boolean? = null,
    val dateError:Boolean? = null,
    val timeError:Boolean? = null,
    val latError:Boolean? = null,
    val lonError:Boolean? = null,
    val stateError:Boolean? = null,
)

fun AppointmentForm.toDomain():Appointment{
    return Appointment(
        id = id,
        title = title,
        date = date,
        time = time,
        lat = lat,
        lon = lon,
        address = address,
        state = state
    )
}