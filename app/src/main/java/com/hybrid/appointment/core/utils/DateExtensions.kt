package com.hybrid.appointment.core.utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

fun Long.toDate(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val instant = Instant.ofEpochMilli(this)
    val date = instant.atZone(ZoneOffset.UTC).toLocalDate().format(formatter)
    return date
}

fun Long.toTime(): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    val instant = Instant.ofEpochMilli(this)
    val time = instant.atZone(ZoneId.systemDefault()).toLocalTime().format(formatter)
    return time
}

fun String.toDateTime(time: String): Long {

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

    val localDateTime = LocalDateTime.parse("$this $time", formatter)

    return localDateTime
        .atZone(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()
}

fun String.toTriggerAlarm(time: String): Long {

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

    val localDateTime = LocalDateTime.parse("$this $time", formatter)

    return localDateTime
        .minusHours(1)
        .atZone(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()
}

