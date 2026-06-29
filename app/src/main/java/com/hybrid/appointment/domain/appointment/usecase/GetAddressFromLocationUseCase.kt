package com.hybrid.appointment.domain.appointment.usecase

import com.hybrid.appointment.domain.appointment.repositories.LocationRepositories
import javax.inject.Inject

class GetAddressFromLocationUseCase @Inject constructor(
    private val locationRepositories: LocationRepositories
) {
    suspend operator fun invoke(lat: Double, lon: Double):String{
        return locationRepositories.getAddress(lat,lon)
    }
}