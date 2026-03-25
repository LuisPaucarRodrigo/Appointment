package com.hybrid.appointment.ui.components.layouts

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.Dot
import com.google.android.gms.maps.model.Gap
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun GoogleMapAppointment(
    cameraPositionState: CameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(-16.3989, -71.5369), 12f)
    },
    uiSettings: MapUiSettings = MapUiSettings(zoomControlsEnabled = false, zoomGesturesEnabled = true),
    polyLines: List<List<LatLng>>? = null,
    markers:List<MarkerList>? = null ,
    isMyLocationEnabled: Boolean = false,
    onMapClick:((LatLng) -> Unit)? = null,
    canPosition: Boolean = false
){
    var selectedLocation by remember { mutableStateOf<LatLng?>(null) }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        uiSettings = uiSettings,
        properties = MapProperties(
            isMyLocationEnabled = isMyLocationEnabled
        ),
        onMapClick = { latLng ->
            if (canPosition) {
                selectedLocation = latLng
                onMapClick?.invoke(latLng)
            }
        }
    ) {

        markers?.let { mar ->
            mar.forEach {
                Marker(
                    state = MarkerState(
                        position = it.latLng
                    ),
                    title = it.description
                )
            }
        }

        polyLines?.let { pol ->
            pol.forEachIndexed { index , it ->
                Polyline(
                    points = it,
                    color = when(index) {
                        0 -> Color.Blue
                        else -> Color.LightGray
                    },
                    width = 25F,
                    pattern = listOf(Dot(), Gap(15f))
                )
            }
        }

        selectedLocation?.let {
            Marker(
                state = MarkerState(position = it),
                title = "Ubicación seleccionada"
            )
        }
    }
}

data class MarkerList(
    val latLng: LatLng,
    val description:String
)