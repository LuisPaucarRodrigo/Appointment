package com.hybrid.appointment.domain.settings

import com.google.android.gms.common.api.ResolvableApiException

sealed class GpsState {
    object Enabled : GpsState()
    object Disabled : GpsState()
    data class NeedsResolution(val exception: ResolvableApiException) : GpsState()
}