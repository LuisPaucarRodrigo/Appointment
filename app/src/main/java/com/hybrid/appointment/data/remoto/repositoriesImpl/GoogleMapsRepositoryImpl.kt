package com.hybrid.appointment.data.remoto.repositoriesImpl

import com.google.android.gms.maps.model.LatLng
import com.hybrid.appointment.data.remoto.entities.LatLngData
import com.hybrid.appointment.data.remoto.entities.LatLngWrapper
import com.hybrid.appointment.data.remoto.entities.LocationWrapper
import com.hybrid.appointment.data.remoto.entities.RouteRequest
import com.hybrid.appointment.data.remoto.services.GoogleRoutesApi
import com.hybrid.appointment.data.remoto.utils.decodeAllRoutes
import com.hybrid.appointment.core.utils.Result
import com.hybrid.appointment.domain.appointment.repositories.GoogleMapsRepositories
import javax.inject.Inject

class GoogleMapsRepositoryImpl @Inject constructor(
    private val googleRoutesApi: GoogleRoutesApi
): GoogleMapsRepositories {
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