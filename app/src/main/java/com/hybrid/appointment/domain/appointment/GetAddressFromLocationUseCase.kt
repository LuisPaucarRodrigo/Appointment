package com.hybrid.appointment.domain.appointment

import javax.inject.Inject

class GetAddressFromLocationUseCase @Inject constructor(
    private val locationRepositories: GeocodeRepositories
) {
    suspend operator fun invoke(lat: Double, lon: Double):String{
        return locationRepositories.getAddress(lat,lon)
    }
}