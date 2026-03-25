package com.hybrid.appointment.domain.appointment

import com.google.android.gms.maps.model.LatLng

interface GeocodeRepositories {
    suspend fun getAddress(lat: Double, lon: Double): String
}