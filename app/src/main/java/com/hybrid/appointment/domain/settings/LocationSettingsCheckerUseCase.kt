package com.hybrid.appointment.domain.settings

import kotlin.coroutines.resume
import android.content.Context
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject

class LocationSettingsCheckerUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {
    suspend operator fun invoke(): GpsState{
        return suspendCancellableCoroutine { cont ->

            val client = LocationServices.getSettingsClient(context)

            val request = LocationSettingsRequest.Builder()
                .addLocationRequest(
                    LocationRequest.Builder(
                        Priority.PRIORITY_HIGH_ACCURACY,
                        10000
                    ).build()
                ).build()

            client.checkLocationSettings(request)
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