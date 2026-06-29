package com.hybrid.appointment.domain.appointment.validate

import javax.inject.Inject

class ValidateTitleUseCase @Inject constructor() {
    fun normalize(text:String): String = text.trim()
    operator fun invoke(title:String): Boolean{
        val normalized = normalize(title)
        if (normalized.length !in 3..40) return true
        if (!normalized.any{it.isLetter()}) return true
        if ("  " in normalized) return true
        return false
    }
}