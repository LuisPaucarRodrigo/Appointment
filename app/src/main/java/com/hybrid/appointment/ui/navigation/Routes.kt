package com.hybrid.appointment.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed class Routes: NavKey{
    @Serializable data object AppointmentListScreen: Routes()
    @Serializable data class AppointmentDetailScreen(val appointmentId:Long): Routes()
    @Serializable data object Form:Routes(){
        @Serializable data object AppointmentFormScreen:Routes()
        @Serializable data object AppointmentMapFormScreen:Routes()
    }
}