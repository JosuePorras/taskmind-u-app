package com.moviles.taskmind.utils

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.ColorUtils

fun parseColorString(colorString: String): Color {
    return try {
        Color(android.graphics.Color.parseColor(colorString))
    } catch (e: IllegalArgumentException) {
        Color.Gray
    }
}

fun darkenColorHex(hexColor: String, factor: Float = 0.8f): Color {
    return try {
        val original = android.graphics.Color.parseColor(hexColor)
        val darkened = ColorUtils.blendARGB(original, android.graphics.Color.BLACK, 1 - factor)
        Color(darkened)
    } catch (e: IllegalArgumentException) {
        Color.Gray
    }
}

fun Color.toHexString(): String {
    val red = (red * 255).toInt().toString(16).padStart(2, '0')
    val green = (green * 255).toInt().toString(16).padStart(2, '0')
    val blue = (blue * 255).toInt().toString(16).padStart(2, '0')
    return "#$red$green$blue"
}