package com.hybrid.appointment.data.local.repositoriesImpl

import android.content.Context
import android.location.Geocoder
import com.hybrid.appointment.domain.appointment.GeocodeRepositories
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Locale
import javax.inject.Inject

class GeocodeRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : GeocodeRepositories {

    override suspend fun getAddress(lat: Double, lon: Double): String {
        return try {
            val geocoder = Geocoder(context, Locale.getDefault())
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
}