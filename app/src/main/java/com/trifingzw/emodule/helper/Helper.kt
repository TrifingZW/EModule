package com.trifingzw.emodule.helper

import java.text.DecimalFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun formatSize(sizeBytes: Long): String {
    val kiloByte = 1024
    val megaByte = kiloByte * 1024
    val gigaByte = megaByte * 1024

    val numberFormat = DecimalFormat("#.##")

    return when {
        sizeBytes >= gigaByte -> {
            val sizeInGB = sizeBytes.toDouble() / gigaByte
            "${numberFormat.format(sizeInGB)} GB"
        }

        sizeBytes >= megaByte -> {
            val sizeInMB = sizeBytes.toDouble() / megaByte
            "${numberFormat.format(sizeInMB)} MB"
        }

        sizeBytes >= kiloByte -> {
            val sizeInKB = sizeBytes.toDouble() / kiloByte
            "${numberFormat.format(sizeInKB)} KB"
        }

        else -> "$sizeBytes Bytes"
    }
}

fun formatTime(timeBites: Long): String {
    val dateTime = Instant.ofEpochMilli(timeBites)
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    return dateTime.format(formatter)
}