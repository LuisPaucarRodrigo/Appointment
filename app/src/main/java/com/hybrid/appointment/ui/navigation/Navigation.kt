package com.hybrid.appointment.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.hybrid.appointment.ui.screen.appointment.detail.AppointmentDetailScreen
import com.hybrid.appointment.ui.screen.appointment.list.AppointmentListScreen

@Composable
fun Navigation(modifier: Modifier) {
    val backStack = rememberNavBackStack(Routes.AppointmentListScreen)

    NavDisplay(
        backStack = backStack,
        onBack = { if (backStack.size > 1) backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<Routes.AppointmentListScreen>{
                AppointmentListScreen(
                    gotoAppointmentForm = { backStack.add(Routes.Form) },
                    gotoAppointmentDetail = { backStack.add(Routes.AppointmentDetailScreen(it)) }
                )
            }
            entry<Routes.Form> {
                FormNavigation(
                    backToNavigation = { backStack.removeLastOrNull() }
                )
            }
            entry<Routes.AppointmentDetailScreen> { AppointmentDetailScreen(appointmentId = it.appointmentId) }

        },
        modifier = modifier
    )
}