package com.hybrid.appointment.ui.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.hybrid.appointment.ui.screen.appointment.detail.AppointmentDetailScreen
import com.hybrid.appointment.ui.screen.appointment.form.AppointmentFormScreen
import com.hybrid.appointment.ui.screen.appointment.form.AppointmentMapFormScreen
import com.hybrid.appointment.ui.screen.appointment.list.AppointmentListScreen

@Composable
fun Navigation(modifier: Modifier) {
    val backStack = rememberNavBackStack(Routes.AppointmentListScreen)
    BackHandler(enabled = backStack.size > 1) {
        backStack.removeLastOrNull()
    }

    NavDisplay(
        backStack = backStack,
        entryProvider = entryProvider {
            entry<Routes.AppointmentListScreen>{ AppointmentListScreen(
                gotoAppointmentForm = { backStack.add(Routes.AppointmentFormScreen(null)) },
                gotoAppointmentDetail = { backStack.add(Routes.AppointmentDetailScreen(it)) }
            ) }
            entry<Routes.AppointmentDetailScreen> { AppointmentDetailScreen(appointmentId = it.appointmentId) }
            entry<Routes.AppointmentFormScreen> {
                AppointmentFormScreen(
                    backToAppointmentListScreen = { backStack.removeLastOrNull() },
                    goToAppointmentMapFormScreen = { backStack.add(Routes.AppointmentMapFormScreen) }
                )
            }
            entry<Routes.AppointmentMapFormScreen> {
                AppointmentMapFormScreen(
                    backToForm = { backStack.removeLastOrNull() }
                )
            }
        },
        modifier = modifier
    )
}