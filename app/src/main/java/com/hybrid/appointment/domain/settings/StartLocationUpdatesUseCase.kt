package com.hybrid.appointment.domain.settings

import com.google.android.gms.maps.model.LatLng
import com.hybrid.appointment.domain.appointment.repositories.LocationRepositories
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StartLocationUpdatesUseCase @Inject constructor(
    private val locationRepositories: LocationRepositories
) {
    operator fun invoke(): Flow<LatLng> {
        return locationRepositories.getCurrentLocation()
    }
}