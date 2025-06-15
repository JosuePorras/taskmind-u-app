package com.moviles.taskmind.viewmodel

import androidx.lifecycle.ViewModel
import com.moviles.taskmind.models.Event

class CalendarViewModel : ViewModel() {
    val events = listOf(
        Event(
            id = 1,
            name = "Midterm",
            description = "Midterm Exam",
            courseId = 101,
            date = "2025-03-14",
            time = "09:00",
            type = "Examen",
            priority = "high"
        ),
        Event(
            id = 2,
            name = "Presentation",
            description = "Final project presentation",
            courseId = 102,
            date = "2025-03-25",
            time = "14:00",
            type = "Presentación",
            priority = "medium"
        ),
        Event(
            id = 2,
            name = "Presentation Metodos",
            description = "Final project presentation",
            courseId = 102,
            date = "2025-03-25",
            time = "18:00",
            type = "Presentación",
            priority = "medium"
        ),
        Event(
            id = 3,
            name = "UI Review",
            description = "Submit UI prototype",
            courseId = 103,
            date = "2025-03-25",
            time = "16:00",
            type = "Proyecto",
            priority = "low"
        )
    )
}