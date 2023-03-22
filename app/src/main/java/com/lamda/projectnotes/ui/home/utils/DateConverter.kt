package com.lamda.projectnotes.ui.home.utils

import java.text.SimpleDateFormat
import java.util.*

fun dateConverter(timestamp: Long): String {
    val format = "dd/MMM/yyyy"
    val sdf = SimpleDateFormat(format, Locale.getDefault())
    sdf.timeZone = TimeZone.getDefault()
    return sdf.format(Date(timestamp * 1000))
}