package com.hybrid.appointment.ui.screen.detail

import com.google.android.gms.maps.model.LatLng
import com.hybrid.appointment.domain.appointment.entities.Appointment
import com.hybrid.appointment.domain.appointment.entities.AppointmentState

data class AppointmentDetailUiState(
    val appointment: AppointmentUi? = null,
    val isLoading: Boolean = true
)

data class AppointmentUi(
    val id:Long,
    val title:String,
    val date:String,
    val time:String,
    val latLon: LatLng,
    val address:String,
    val state: AppointmentState
)

sealed class AppointmentDetailEvent{
    data class Error(val errorMessage:String):AppointmentDetailEvent()
    data class Success(val successMessage:String): AppointmentDetailEvent()
}

data class ShowError(
    val message: String? = null,
    val isShow: Boolean = false
)

fun Appointment.toUi(): AppointmentUi{
    return AppointmentUi(
        id = id,
        title = title,
        date = date,
        time = time,
        latLon = LatLng(lat,lon),
        address = address,
        state = state
    )
}

fun AppointmentUi.toDomain(): Appointment{
    return Appointment(
        id = id,
        title = title,
        date = date,
        time = time,
        lat = latLon.latitude,
        lon = latLon.longitude,
        address = address,
        state = state
    )
}