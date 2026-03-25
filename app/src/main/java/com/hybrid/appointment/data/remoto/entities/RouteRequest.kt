package com.hybrid.appointment.data.remoto.entities

import com.google.android.gms.maps.model.LatLng

data class RouteRequest(
    val origin: LocationWrapper,
    val destination: LocationWrapper,
    val travelMode: String = "DRIVE",
    val requestedReferenceRoutes: List<String> = listOf("SHORTER_DISTANCE")
)

data class LocationWrapper(
    val location: LatLngWrapper
)

data class LatLngWrapper(
    val latLng: LatLngData
)

data class LatLngData(
    val latitude: Double,
    val longitude: Double
)

//Response
data class RouteResponse(
    val routes: List<Route>
)

data class Route(
    val distanceMeters: Int,
    val duration: String,
    val polyline: Polyline
)

data class Polyline(
    val encodedPolyline: String
)