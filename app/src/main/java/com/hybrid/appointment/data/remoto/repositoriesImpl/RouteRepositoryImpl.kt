package com.hybrid.appointment.data.remoto.repositoriesImpl

import com.google.android.gms.maps.model.LatLng
import com.hybrid.appointment.data.remoto.entities.LatLngData
import com.hybrid.appointment.data.remoto.entities.LatLngWrapper
import com.hybrid.appointment.data.remoto.entities.LocationWrapper
import com.hybrid.appointment.data.remoto.entities.RouteRequest
import com.hybrid.appointment.data.remoto.routes.GoogleRoutesApi
import com.hybrid.appointment.data.remoto.utils.decodeAllRoutes
import com.hybrid.appointment.data.remoto.utils.decodePolyline
import com.hybrid.appointment.domain.Result
import com.hybrid.appointment.domain.appointment.RouteRepositories
import javax.inject.Inject

class RouteRepositoryImpl @Inject constructor(
    private val googleRoutesApi: GoogleRoutesApi
): RouteRepositories {
    override suspend fun getRoute(start: LatLng, end: LatLng): Result<List<List<LatLng>>> {
        return try {
            val request = RouteRequest(
                origin = LocationWrapper(
                    location = LatLngWrapper(
                        latLng = LatLngData(start.latitude,start.longitude)
                    )
                ),
                destination = LocationWrapper(
                    location = LatLngWrapper(
                        latLng = LatLngData(end.latitude,end.longitude)
                    )
                )
            )
            val response = googleRoutesApi.getRoute(request)

            if (response.routes.isEmpty()) {
                return Result.Error("No route found")
            }

            val decoded = decodeAllRoutes(response.routes)

            Result.Success(decoded)
        } catch (e: Exception){
            Result.Error(e.message ?: "Unknown error")
        }
    }
}