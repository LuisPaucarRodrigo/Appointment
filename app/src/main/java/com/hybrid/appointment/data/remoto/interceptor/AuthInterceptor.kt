package com.hybrid.appointment.data.remoto.interceptor

import com.hybrid.appointment.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val key = BuildConfig.MAPS_API_KEY
        println("mapsey${key}")
        val polies= "routes.duration,routes.distanceMeters,routes.polyline.encodedPolyline"
        val request = chain.request().newBuilder().apply {
            addHeader("X-Goog-Api-Key",key)
            addHeader("X-Goog-FieldMask",polies)
        }.build()
        return chain.proceed(request)
    }
}