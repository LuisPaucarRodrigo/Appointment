package com.hybrid.appointment.domain.appointment.repositories

import com.google.android.gms.maps.model.LatLng
import com.hybrid.appointment.domain.Result

interface GoogleMapsRepositories {
    suspend fun getRoute(start: LatLng, end: LatLng): Result<List<List<LatLng>>>
}