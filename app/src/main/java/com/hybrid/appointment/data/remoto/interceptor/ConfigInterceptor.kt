package com.hybrid.appointment.data.remoto.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class ConfigInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val requestBuilder: Request.Builder = original.newBuilder()
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
        val request: Request = requestBuilder.build()
        return chain.proceed(request)
    }
}