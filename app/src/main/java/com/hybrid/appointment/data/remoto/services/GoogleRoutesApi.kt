package com.hybrid.appointment.data.remoto.services

import com.hybrid.appointment.data.remoto.entities.RouteRequest
import com.hybrid.appointment.data.remoto.entities.RouteResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface GoogleRoutesApi {
    @POST("directions/v2:computeRoutes")
    suspend fun getRoute(@Body request: RouteRequest): RouteResponse
}