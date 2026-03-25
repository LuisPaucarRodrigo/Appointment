package com.hybrid.appointment.ui.screen.appointment.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.rememberCameraPositionState
import com.hybrid.appointment.domain.appointment.Appointment
import com.hybrid.appointment.domain.appointment.AppointmentState
import com.hybrid.appointment.ui.components.hardware.RequestEnableLocation
import com.hybrid.appointment.ui.screen.appointment.list.StateBadge
import com.hybrid.appointment.ui.components.layouts.BottomBar
import com.hybrid.appointment.ui.components.layouts.GoogleMapAppointment
import com.hybrid.appointment.ui.components.layouts.MarkerList
import com.hybrid.appointment.ui.components.layouts.TopBar
import com.hybrid.appointment.ui.components.permissions.RequestLocationPermission
import kotlinx.coroutines.delay

@Composable
fun AppointmentDetailScreen(
    appointmentId:Long,
    appointmentDetailVM: AppointmentDetailVM = hiltViewModel<AppointmentDetailVM>()
){
    val state by appointmentDetailVM.state.collectAsState()
    val route by appointmentDetailVM.route.collectAsState()
    val userLocation by appointmentDetailVM.userLocation.collectAsState()

    val cameraPositionState = rememberCameraPositionState()

//    val uiError by appointmentDetailVM.uiError.collectAsState(initial = null)
    val uiGps by appointmentDetailVM.uiGps.collectAsState(initial = null)
    val canShowLocation by appointmentDetailVM.canShowLocation.collectAsState()

    var showError by remember { mutableStateOf<ShowError>(ShowError()) }

    DisposableEffect(Unit) {
        onDispose {
            appointmentDetailVM.clearAllState()
        }
    }

    LaunchedEffect(Unit) {
        appointmentDetailVM.setAppointmentId(appointmentId)
    }

    LaunchedEffect(state.appointment?.lat) {
        if(userLocation == null){
            state.appointment?.let {
                cameraPositionState.position =
                    CameraPosition.fromLatLngZoom(LatLng(it.lat, it.lon), 16f)
            }
        }
    }

    LaunchedEffect(userLocation) {
        userLocation?.let {
            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLngZoom(it, 16f),
                durationMs = 1000
            )
        }
    }

    LaunchedEffect(Unit) {
        appointmentDetailVM.uiError.collect {
            delay(500)
            showError = ShowError(message = it.message, isShow = true)
            delay(2000)
            showError = ShowError()
        }
    }

    Scaffold(
        topBar = { TopBarDetail() },
        bottomBar = { BottomBarDetail(state) },
        floatingActionButton = { FloatActionButton(state = state, action = { appointmentDetailVM.updateAppointment() }) }
    ){ padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            ContentDetail(
                route = route,
                canShowLocation = canShowLocation,
                appointment = state.appointment,
                cameraPositionState = cameraPositionState
            )

            AnimatedVisibility(
                visible = showError.isShow,
                enter = slideInVertically(initialOffsetY = { -it }),
                exit = slideOutVertically(targetOffsetY = { -it })
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    colors = CardColors(
                        containerColor = Color(0xFFEC5E5E),
                        contentColor = Color.White,
                        disabledContainerColor = Color.Red,
                        disabledContentColor = Color.White,
                    ),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                ) {
                    Text(
                        text = showError.message.toString(),
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
//            if (showError.isShow){
//                Card(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(8.dp),
//                    colors = CardColors(
//                        containerColor = Color(0xFFEC5E5E),
//                        contentColor = Color.White,
//                        disabledContainerColor = Color.Red,
//                        disabledContentColor = Color.White,
//                    ),
//                    shape = RoundedCornerShape(12.dp),
//                    elevation = CardDefaults.cardElevation(4.dp),
//                ) {
//                    Text(
//                        text = showError.message.toString(),
//                        modifier = Modifier.padding(16.dp)
//                    )
//                }
//
//            }
        }
    }

    uiGps?.let { event ->
        RequestEnableLocation(
            exception = event.resolvable,
            onGpsEnabled = {
                appointmentDetailVM.checkGps()
            },
            onGpsDisabled = { appointmentDetailVM.showError("Gps is required for the tracking") }
        )
    }

    RequestLocationPermission(
        onPermissionGranted = { appointmentDetailVM.onPermissionResult(true) },
        onPermissionDenied = { appointmentDetailVM.onPermissionResult(false) }
    )
}

@Composable
fun ContentDetail(
    route: List<List<LatLng>>,
    appointment: Appointment?,
    cameraPositionState: CameraPositionState,
    canShowLocation: Boolean
){
    GoogleMapAppointment(
        cameraPositionState = cameraPositionState,
        isMyLocationEnabled = canShowLocation,
        polyLines = route,
        markers = appointment?.let {
            listOf(
                MarkerList(
                    latLng = LatLng(it.lat,it.lon),
                    description = "${it.title}-${it.address}-${it.date}"
                )
            )
        }
    )
//    appointment?.let {
//        if(it.state != AppointmentState.VISITADO){
//            FloatActionButton(
//                text = when(it.state){
//                    AppointmentState.NO_VISITADO -> "Go"
//                    AppointmentState.EN_RUTA -> "End"
//                    else -> ""
//                },
//                modifier = Modifier
//                    .align(Alignment.BottomEnd)
//                    .padding(16.dp),
//                action = { updateAppointment() }
//            )
//        }
//    }
}

@Composable
fun FloatActionButton(
    state:AppointmentDetailUiState,
    action:() -> Unit,
){
    if(state.appointment?.state != AppointmentState.VISITADO){
        FloatingActionButton(
            onClick = { action() },
        ) {
            Text(
                text = when(state.appointment?.state){
                    AppointmentState.NO_VISITADO -> "Go"
                    AppointmentState.EN_RUTA -> "End"
                    else -> ""
                },
                modifier = Modifier
                    .padding(16.dp),
            )
        }
    }
}

@Composable
fun TopBarDetail(){
    TopBar(
        action = {}
    )
}

@Composable
fun BottomBarDetail(
    state: AppointmentDetailUiState
){
    when {
        state.isLoading -> {
            BottomBar("Loading...")
        }
        else -> {
            state.appointment?.let {
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(16.dp)
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = it.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color.Black
                        )

                        StateBadge(it.state)
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
                                text = it.address,
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
                                text = it.time,
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
                                text = it.date,
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }
}