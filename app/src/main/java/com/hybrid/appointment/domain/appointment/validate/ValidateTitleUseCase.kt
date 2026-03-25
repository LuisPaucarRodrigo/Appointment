package com.hybrid.appointment.domain.appointment.validate

import javax.inject.Inject

class ValidateTitleUseCase @Inject constructor() {
    fun normalize(text:String): String = text.trim()
    operator fun invoke(title:String): Boolean{
        val normalized = normalize(title)
        if (normalized.length !in 3..40) return false
        if (!normalized.any{it.isLetter()}) return false
        if ("  " in normalized) return false
        return true
    }
}