package com.hybrid.appointment.ui.screen.appointment.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.google.android.gms.maps.model.LatLng
import com.hybrid.appointment.domain.appointment.Appointment
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import com.hybrid.appointment.domain.appointment.AppointmentState
import com.hybrid.appointment.ui.components.layouts.BottomBar
import com.hybrid.appointment.ui.components.layouts.GoogleMapAppointment
import com.hybrid.appointment.ui.components.layouts.MarkerList
import com.hybrid.appointment.ui.components.layouts.TopBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentListScreen(
    gotoAppointmentForm: () -> Unit,
    gotoAppointmentDetail: (Long) -> Unit,
    appointmentListVM: AppointmentListVM = hiltViewModel()
) {
    val state by appointmentListVM.state.collectAsState()
    BottomSheetScaffold(
        containerColor = Color.White,
        topBar = { TopBarList{ gotoAppointmentForm() } },
        sheetContainerColor = Color(0xFFF5F0F0),
        sheetContent = {
            SheetContentList(
                gotoAppointmentDetail = { gotoAppointmentDetail(it) },
                appointmentListVM = appointmentListVM ,
                state = state
            )
        },
        sheetPeekHeight = 120.dp
    ) { padding ->
        GoogleMapList(padding = padding,appointments= state.appointments)
    }
}

@Composable
fun GoogleMapList(
    padding: PaddingValues,
    appointments: List<Appointment>
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        GoogleMapAppointment(
            markers = appointments.map {  MarkerList(
                latLng = LatLng(it.lat,it.lon),
                description = "${it.title}-${it.address}-${it.date}"
            ) }
        )
    }
}

@Composable
fun SheetContentList(
    gotoAppointmentDetail:(Long) ->Unit,
    appointmentListVM: AppointmentListVM,
    state: AppointmentListUiState
) {
    when {
        state.isLoading -> {
            BottomBar("Loading...")
        }
        state.appointments.isEmpty() -> {
            BottomBar("There isn't registers")
        }
        else -> {
            AppointmentList(
                appointments = state.appointments,
                gotoAppointmentDetail = { gotoAppointmentDetail(it) },
                deleteAppointment = { appointmentListVM.deleteAppointment(it) }
            )
        }
    }
}

@Composable
fun TopBarList(
    gotoAppointmentForm:() -> Unit
){
    TopBar(
        action = {
            Text(
                text = "Agregar",
                color = Color(0xFF1976D2),
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable { gotoAppointmentForm() }
            )
        }
    )
}

@Composable
fun AppointmentList(
    appointments: List<Appointment>,
    gotoAppointmentDetail:(Long) -> Unit,
    deleteAppointment:(Long) -> Unit,
) {
    LazyColumn {
        items(appointments) { appointment ->
            AppointmentItem(
                appointment = appointment,
                gotoAppointmentDetail = { gotoAppointmentDetail(appointment.id) },
                deleteAppointment = { deleteAppointment(appointment.id) }
            )
        }
    }
}

@Composable
fun AppointmentItem(
    appointment: Appointment,
    gotoAppointmentDetail:() -> Unit,
    deleteAppointment:() -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardColors(
            containerColor = Color.White,
            contentColor = Color.White,
            disabledContainerColor = Color.White,
            disabledContentColor = Color.White
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = appointment.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )

                StateBadge(appointment.state)
            }


            Row(verticalAlignment = Alignment.CenterVertically) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = Color.Red,
                        modifier = Modifier.size(16.dp)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = appointment.address,
                        fontSize = 12.sp,
                        color = Color.Gray,
                        maxLines = 2
                    )
                }


                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AccessTime,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = Color.Gray
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = appointment.time,
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column {
                    Text(
                        text = appointment.date,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    if (appointment.state != AppointmentState.VISITADO){
                        Text(
                            text = "Cancelar visita",
                            fontSize = 13.sp,
                            color = Color.Red,
                            modifier = Modifier.clickable { deleteAppointment() }
                        )
                    }
                }
                Column {
                    Text(
                        text = "Detalle",
                        fontSize = 13.sp,
                        color = Color(0xFF8CB9F3),
                        modifier = Modifier.clickable { gotoAppointmentDetail() }
                    )
                }
            }
        }
    }
}

@Composable
fun StateBadge(state: AppointmentState) {
    val (color, text) = when (state) {
        AppointmentState.VISITADO -> Color(0xFF4CAF50) to "VISITADO"
        AppointmentState.EN_RUTA -> Color(0xFF03A9F4) to "EN RUTA"
        else -> Color(0xFFF44336) to "NO VISITADO"
    }

    Box(
        modifier = Modifier
            .background(color, RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 2.dp)
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}