package com.example.wellme.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object TimeData {
    fun getCurrentTime(): String {
        return SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
    }

    fun getCurrentDate(): String {
        return SimpleDateFormat("dd/MM", Locale.getDefault()).format(Date())
    }
}