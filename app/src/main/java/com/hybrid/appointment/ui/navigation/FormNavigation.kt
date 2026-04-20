package com.hybrid.appointment.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.hybrid.appointment.ui.screen.appointment.form.AppointmentFormScreen
import com.hybrid.appointment.ui.screen.appointment.form.AppointmentFormVM
import com.hybrid.appointment.ui.screen.appointment.form.AppointmentMapFormScreen

@Composable
fun FormNavigation(
    backToNavigation:()->Unit
) {
    val backStack = rememberNavBackStack(Routes.Form.AppointmentFormScreen)
    val vm:AppointmentFormVM = hiltViewModel<AppointmentFormVM>()

    NavDisplay(
        backStack = backStack,
        onBack = { if (backStack.size > 1) backStack.removeLastOrNull() else backToNavigation() },
        entryProvider = entryProvider {
            entry<Routes.Form.AppointmentFormScreen>{
                AppointmentFormScreen(
                    backToAppointmentListScreen = { backToNavigation() },
                    goToAppointmentMapFormScreen = { backStack.add(Routes.Form.AppointmentMapFormScreen) },
                    appointmentFormVM = vm
                )
            }
            entry<Routes.Form.AppointmentMapFormScreen>{
                AppointmentMapFormScreen(
                    backToForm = { backStack.removeLastOrNull() },
                    appointmentFormVM = vm
                )
            }
        }
    )
}