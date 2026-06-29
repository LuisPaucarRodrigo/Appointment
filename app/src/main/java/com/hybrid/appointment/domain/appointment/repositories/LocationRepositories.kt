package com.hybrid.appointment.domain.appointment.repositories

import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow

interface LocationRepositories {
    suspend fun getAddress(lat: Double, lon: Double): String
    fun getCurrentLocation(): Flow<LatLng>
}