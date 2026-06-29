package com.hybrid.appointment.domain.appointment.repositories

import com.hybrid.appointment.domain.settings.GpsState

interface LocationSettingsRepository {
    suspend fun checkGpsState(): GpsState
}