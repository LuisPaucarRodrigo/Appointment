package com.hybrid.appointment.domain.settings

import com.hybrid.appointment.domain.appointment.repositories.LocationSettingsRepository
import javax.inject.Inject

class LocationSettingsCheckerUseCase @Inject constructor(
    private val locationSettingsRepository: LocationSettingsRepository
) {
    suspend operator fun invoke(): GpsState{
        return locationSettingsRepository.checkGpsState()
    }
}