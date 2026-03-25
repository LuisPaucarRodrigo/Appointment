package com.hybrid.appointment.domain.appointment

import com.google.android.gms.maps.model.LatLng
import com.hybrid.appointment.domain.Result

interface RouteRepositories {
    suspend fun getRoute(start: LatLng, end: LatLng): Result<List<List<LatLng>>>
}