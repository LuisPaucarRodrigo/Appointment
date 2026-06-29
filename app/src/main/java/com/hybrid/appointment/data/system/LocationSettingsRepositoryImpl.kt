package com.hybrid.appointment.data.system

import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import com.hybrid.appointment.domain.appointment.repositories.LocationSettingsRepository
import com.hybrid.appointment.domain.settings.GpsState
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class LocationSettingsRepositoryImpl @Inject constructor(
    private val settingsClient: SettingsClient
) : LocationSettingsRepository {
    override suspend fun checkGpsState(): GpsState {
        return suspendCancellableCoroutine { cont ->
            val request = LocationSettingsRequest.Builder()
                .addLocationRequest(
                    LocationRequest.Builder(
                        Priority.PRIORITY_HIGH_ACCURACY,
                        10000
                    ).build()
                ).build()

            settingsClient.checkLocationSettings(request)
                .addOnSuccessListener {
                    cont.resume(GpsState.Enabled)
                }
                .addOnFailureListener { exception ->
                    if (exception is ResolvableApiException) {
                        cont.resume(GpsState.NeedsResolution(exception))
                    } else {
                        cont.resume(GpsState.Disabled)
                    }
                }
        }
    }
}