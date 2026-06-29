package com.hybrid.appointment.data.local.repositoriesImpl

import android.annotation.SuppressLint
import android.location.Geocoder
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.hybrid.appointment.domain.appointment.repositories.LocationRepositories
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class GeocodeRepositoryImpl @Inject constructor(
    private var geocoder: Geocoder,
    private val fusedLocationClient: FusedLocationProviderClient
) : LocationRepositories {

    override suspend fun getAddress(lat: Double, lon: Double): String {
        return try {
            val addresses = geocoder.getFromLocation(lat, lon, 1)
            if (!addresses.isNullOrEmpty()) {
                addresses[0].getAddressLine(0) ?: "Dirección no disponible"
            } else {
                "Dirección no encontrada"
            }

        } catch (e: Exception) {
            "Error obteniendo dirección"
        }
    }

    @SuppressLint("MissingPermission")
    override fun getCurrentLocation(): Flow<LatLng> = callbackFlow {
        val request = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            2000L
        ).build()

        val callback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                val location = result.lastLocation ?: return
                trySend(LatLng(location.latitude, location.longitude))
            }
        }

        fusedLocationClient.requestLocationUpdates(
            request,
            callback,
            Looper.getMainLooper()
        )

        awaitClose {
            fusedLocationClient.removeLocationUpdates(callback)
        }
    }
}