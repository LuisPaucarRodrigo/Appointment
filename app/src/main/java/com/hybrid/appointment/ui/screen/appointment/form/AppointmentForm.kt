package com.hybrid.appointment.ui.screen.appointment.form

import com.hybrid.appointment.domain.appointment.AppointmentState

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
