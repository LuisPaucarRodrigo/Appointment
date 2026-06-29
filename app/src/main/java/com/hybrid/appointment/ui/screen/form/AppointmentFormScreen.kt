package com.hybrid.appointment.ui.screen.form

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hybrid.appointment.core.ui.AppTextField
import com.hybrid.appointment.core.ui.DatePickerModal
import com.hybrid.appointment.core.ui.TimePickerModal
import com.hybrid.appointment.core.ui.TopBar

@Composable
fun AppointmentFormScreen(
    backToAppointmentListScreen:() ->Unit,
    goToAppointmentMapFormScreen:()->Unit,
    appointmentFormVM: AppointmentFormVM
)
{
    val state by appointmentFormVM.state.collectAsState()
    val canSave = appointmentFormVM.canSave()
    var openDateDialog by remember { mutableStateOf(false) }
    var openTimeDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        appointmentFormVM.events.collect { events ->
            when(events){
                is AppointmentFormEvent.Success -> { backToAppointmentListScreen() }
                is AppointmentFormEvent.Error -> {  }
            }
        }
    }

    Scaffold(
        topBar = { TopBar{} },
        containerColor = Color.White,
    ) { padding ->
        ContentForm(
            isLoading = state.isLoading,
            padding = padding,
            canSave =  canSave,
            openDateDialog = openDateDialog,
            openTimeDialog = openTimeDialog,
            openDateDialogChange = { openDateDialog = it },
            openTimeDialogChange = { openTimeDialog = it },
            appointmentFormVM = appointmentFormVM,
            appointmentForm = state.appointment,
            appointmentFormError = state.appointmentError,
            goToAppointmentMapFormScreen = { goToAppointmentMapFormScreen()}
        )
    }

}

@Composable
fun ContentForm(
    isLoading: Boolean,
    padding: PaddingValues,
    appointmentFormVM: AppointmentFormVM,
    appointmentForm: AppointmentForm,
    appointmentFormError: AppointmentFormError,
    goToAppointmentMapFormScreen: () -> Unit,
    openDateDialog: Boolean,
    openTimeDialog: Boolean,
    openDateDialogChange: (Boolean) -> Unit,
    openTimeDialogChange: (Boolean) -> Unit,
    canSave: Boolean
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        AppTextField(
            title = "Title(mín. 3 characters)",
            placeholder = "Ingresa Titulo",
            value = appointmentForm.title,
            isError = appointmentFormError.titleError == true,
            enabled = !isLoading,
            onValueChange = { appointmentFormVM.onAction(AppointmentFormAction.TitleChanged(it)) }
        )

        AppTextField(
            title = "Fecha de Cita",
            placeholder = "Ingresa Fecha",
            value = appointmentForm.date,
            isError = appointmentFormError.dateError == true,
            enabled = !isLoading,
            readOnly = true,
            secondOnClick = { openDateDialogChange(true) },
            onValueChange = { appointmentFormVM.onAction(AppointmentFormAction.DateChanged(it)) },
            trailingIcon = { Icon(Icons.Default.DateRange, contentDescription = null, tint = Color.Black)  }
        )

        AppTextField(
            title = "Hora de Cita",
            placeholder = "Ingresa Hora",
            value = appointmentForm.time,
            isError = appointmentFormError.timeError == true,
            enabled = !isLoading,
            readOnly = true,
            secondOnClick = { openTimeDialogChange(true) },
            onValueChange = { appointmentFormVM.onAction(AppointmentFormAction.TimeChanged(it)) },
            trailingIcon = { Icon(Icons.Default.AccessTime, contentDescription = null,tint = Color.Black)   }
        )

        AppTextField(
            title = "Ubicacion",
            placeholder = "Selecciona ubicacion",
            value = "${appointmentForm.lat} - ${appointmentForm.lon}",
            isError = appointmentFormError.latError == true && appointmentFormError.lonError == true,
            enabled = !isLoading,
            readOnly = true,
            secondOnClick = { goToAppointmentMapFormScreen() },
            onValueChange = {},
            trailingIcon = { Icon(Icons.Default.Map, contentDescription = null,tint = Color.Black)   }
        )

//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically) {
//
//            Box(
//                modifier = Modifier
//                    .weight(1f)
//                    .clickable(onClick = { openDateDialogChange(true) })
//            ) {
//                OutlinedTextField(
//                    value = appointmentForm.date,
//                    onValueChange = {},
//                    label = { Text("Date") },
//                    readOnly = true,
//                    modifier = Modifier.fillMaxWidth(),
//                    isError = appointmentFormError.dateError == false && !canSave && !isLoading,
//                    trailingIcon = { },
//                )
//                Box(
//                    modifier = Modifier
//                        .matchParentSize()
//                        .pointerInput(Unit) {
//                            awaitPointerEventScope {
//                                while (true) {
//                                    awaitPointerEvent()
//                                }
//                            }
//                        }
//                )
//            }
//        }
//
//        Row(modifier = Modifier.fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically) {
//            Box(
//                modifier = Modifier
//                    .weight(1f)
//                    .clickable(onClick = { openTimeDialogChange(true) })
//            ) {
//                OutlinedTextField(
//                    value = appointmentForm.time,
//                    onValueChange = {},
//                    label = { Text("Time") },
//                    readOnly = true,
//                    modifier = Modifier.fillMaxWidth(),
//                    isError = appointmentFormError.timeError == false && !canSave && !isLoading,
//                    trailingIcon = { Icon(Icons.Default.AccessTime, contentDescription = null,tint = Color.Black) }
//                )
//                Box(
//                    modifier = Modifier
//                        .matchParentSize()
//                        .pointerInput(Unit) {
//                            awaitPointerEventScope {
//                                while (true) {
//                                    awaitPointerEvent()
//                                }
//                            }
//                        }
//                )
//            }
//        }
//
//        Row(modifier = Modifier.fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically) {
//            Box(modifier = Modifier.weight(1f).clickable(onClick = { goToAppointmentMapFormScreen() })) {
//                OutlinedTextField(
//                    value = "${appointmentForm.lat} - ${appointmentForm.lon}",
//                    onValueChange = {},
//                    label = { Text("Location") },
//                    readOnly = true,
//                    modifier = Modifier.fillMaxWidth(),
//                    isError = (appointmentFormError.latError == false && appointmentFormError.lonError == false) && !canSave && !isLoading,
//                    trailingIcon = { Icon(Icons.Default.Map, contentDescription = null,tint = Color.Black) }
//                )
//                Box(
//                    modifier = Modifier
//                        .matchParentSize()
//                        .pointerInput(Unit) {
//                            awaitPointerEventScope {
//                                while (true) {
//                                    awaitPointerEvent()
//                                }
//                            }
//                        }
//                )
//            }
//        }

        Button(
            onClick = { appointmentFormVM.onSave() },
            modifier = Modifier.fillMaxWidth(),
            enabled = canSave && !isLoading,
            colors = ButtonColors(
                contentColor = Color.White,
                containerColor = Color(0xFF1976D2),
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.White,
            )
        ) {
            Text("Save Appointment")
        }
    }
    if (openDateDialog) {
        DatePickerModal(
            { appointmentFormVM.onAction(AppointmentFormAction.DateChanged(it)) },
            { openDateDialogChange(false) }
        )
    }

    if (openTimeDialog) {
        TimePickerModal(
            { appointmentFormVM.onAction(AppointmentFormAction.TimeChanged(it)) },
            { openTimeDialogChange(false) }
        )
    }
}