package com.hybrid.appointment.domain.appointment.validate

import javax.inject.Inject

class ValidateRequiredUseCase @Inject constructor() {
    fun normalize(text:String):String = text.trim()
    operator fun invoke(text:String): Boolean{
        val normalized = normalize(text)
        if (normalized.isEmpty()) return false
        if ("  " in normalized) return false
        return true
    }
}