package com.moviles.taskmind.utils

import androidx.compose.ui.graphics.Color

fun parseColorString(colorString: String): Color {
    return try {
        Color(android.graphics.Color.parseColor(colorString))
    } catch (e: IllegalArgumentException) {
        Color.Gray
    }
}