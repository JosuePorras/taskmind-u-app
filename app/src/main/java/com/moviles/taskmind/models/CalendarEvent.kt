package com.moviles.taskmind.models

import androidx.compose.ui.graphics.Color

data class CalendarEvent(
    val name: String,
    val description: String,
    val date: String,
    val courseId: Int,
    val color: Color
)
