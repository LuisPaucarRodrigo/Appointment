package com.hybrid.appointment.domain.appointment.usecase

import com.google.android.gms.maps.model.LatLng
import com.hybrid.appointment.domain.Result
import com.hybrid.appointment.domain.appointment.repositories.GoogleMapsRepositories
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetRouteUseCase @Inject constructor(
    private val routeRepositories: GoogleMapsRepositories
) {
    suspend operator fun invoke(start: LatLng, end: LatLng): Result<List<List<LatLng>>>{
        return withContext(Dispatchers.IO) {
            routeRepositories.getRoute(start = start, end = end)
        }
    }
}