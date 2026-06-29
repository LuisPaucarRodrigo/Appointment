package com.hybrid.appointment.ui.screen.form

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.LatLng
import com.hybrid.appointment.ui.components.layouts.GoogleMapAppointment

@Composable
fun AppointmentMapFormScreen(
    backToForm:() -> Unit,
    appointmentFormVM: AppointmentFormVM
) {
    var selectedLocation by remember { mutableStateOf<LatLng?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMapAppointment(
            onMapClick = { selectedLocation = it },
            canPosition = true
        )

        if (selectedLocation != null) {
            FloatingActionButton(
                onClick = {
                    selectedLocation?.let {
                        appointmentFormVM.onAction(AppointmentFormAction.LocationChanged(it.latitude,it.longitude))
                        backToForm()
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Guardar ubicación"
                )
            }
        }
    }
}