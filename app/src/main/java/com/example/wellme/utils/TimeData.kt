package com.example.wellme.utils

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale

object TimeData {

    fun getCurrentTime(): String {
        return SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
    }

    fun getCurrentDate(): String {
        return SimpleDateFormat("dd/MM", Locale.getDefault()).format(Date())
    }

    fun getDayOfTheWeek(): Int {
        return LocalDate.now().dayOfWeek.value
    }

    fun getHourMeridium(hour: String): String {
        return if (hour < "12:00") { "AM" } else { "PM" }
    }
}