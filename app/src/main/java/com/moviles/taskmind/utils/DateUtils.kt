package com.moviles.taskmind.utils

import java.text.SimpleDateFormat
import java.util.*

fun isSameDay(dateStr: String, year: Int, month: Int, day: Int): Boolean {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val inputDate = sdf.parse(dateStr) ?: return false

    val cal1 = Calendar.getInstance().apply { time = inputDate }

    return (cal1.get(Calendar.YEAR) == year &&
            cal1.get(Calendar.MONTH) == month &&
            cal1.get(Calendar.DAY_OF_MONTH) == day)
}

fun formatDateToString(date: Date): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return sdf.format(date)
}