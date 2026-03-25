package com.hybrid.appointment.domain.appointment.usecase

import com.google.android.gms.maps.model.LatLng
import com.hybrid.appointment.domain.Result
import com.hybrid.appointment.domain.appointment.RouteRepositories
import javax.inject.Inject

class GetRouteUseCase @Inject constructor(
    private val routeRepositories: RouteRepositories
) {
    suspend operator fun invoke(start: LatLng, end: LatLng): Result<List<List<LatLng>>>{
        return routeRepositories.getRoute(start = start, end = end)
    }
}