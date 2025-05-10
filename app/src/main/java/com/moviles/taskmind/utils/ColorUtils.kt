package com.moviles.taskmind.utils

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.ColorUtils

fun parseColorString(colorString: String): Color {
    return try {
        Color(android.graphics.Color.parseColor(colorString))
    } catch (e: IllegalArgumentException) {
        Color.Gray
    }
}

fun darkenColorHex(hexColor: String): Color {
    return when(hexColor.uppercase()){
        "#C8ABFC"->Color(0XFF8B5CF6) //purple
        "#F6EBA0"->Color(0XFFF59E0B)//Yellow
        "#ABECBE"->Color(0XFF10B981)//green
        "#A0C6FD"->Color(0XFF2563EB)//blue
        "#FF7B6F"->Color(0xFF800909)//red

        else ->try {
            Color(android.graphics.Color.parseColor(hexColor))
        }catch (e:IllegalArgumentException){
            Color.Gray
        }
    }
}

fun Color.toHexString(): String {
    val red = (red * 255).toInt().toString(16).padStart(2, '0')
    val green = (green * 255).toInt().toString(16).padStart(2, '0')
    val blue = (blue * 255).toInt().toString(16).padStart(2, '0')
    return "#$red$green$blue"
}