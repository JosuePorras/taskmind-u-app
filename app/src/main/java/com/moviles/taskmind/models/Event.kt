package com.moviles.taskmind.models

data class Event(
    val id: Int,
    val name: String,
    val description: String,
    val courseId: Int,
    val date: String,
    val time: String,
    val type: String,
    val priority: String
)