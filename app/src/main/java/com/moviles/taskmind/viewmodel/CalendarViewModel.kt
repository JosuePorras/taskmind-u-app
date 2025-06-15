package com.moviles.taskmind.viewmodel

import androidx.lifecycle.ViewModel
import com.moviles.taskmind.models.Event

class CalendarViewModel : ViewModel() {
    val events = listOf(
        Event(
            id = 1,
            name = "Entrega Informe Final",
            description = "Proyecto final de la materia de Bases de Datos",
            courseId = 201,
            date = "2025-06-15",
            time = "23:59",
            type = "Proyecto",
            priority = "high"
        ),
        Event(
            id = 2,
            name = "Laboratorio de Física",
            description = "Experimento sobre leyes de Newton",
            courseId = 202,
            date = "2025-06-15",
            time = "10:00",
            type = "Laboratorio",
            priority = "medium"
        ),
        Event(
            id = 3,
            name = "Tarea de Álgebra",
            description = "Resolver ejercicios del capítulo 4",
            courseId = 203,
            date = "2025-06-16",
            time = "20:00",
            type = "Tarea",
            priority = "low"
        ),
        Event(
            id = 4,
            name = "Examen Parcial de Historia",
            description = "Primera evaluación del curso",
            courseId = 204,
            date = "2025-06-18",
            time = "08:00",
            type = "Examen",
            priority = "high"
        ),
        Event(
            id = 5,
            name = "Presentación de Proyecto",
            description = "Presentación del avance del proyecto de programación",
            courseId = 205,
            date = "2025-06-20",
            time = "13:00",
            type = "Presentación",
            priority = "medium"
        ),
        Event(
            id = 6,
            name = "Tarea de Ética",
            description = "Ensayo sobre dilemas éticos en la tecnología",
            courseId = 206,
            date = "2025-06-22",
            time = "22:00",
            type = "Tarea",
            priority = "low"
        ),
        Event(
            id = 7,
            name = "Laboratorio de Química",
            description = "Análisis de compuestos orgánicos",
            courseId = 207,
            date = "2025-06-24",
            time = "11:30",
            type = "Laboratorio",
            priority = "medium"
        ),
        Event(
            id = 8,
            name = "Defensa del Proyecto Final",
            description = "Presentación y defensa ante el jurado",
            courseId = 208,
            date = "2025-06-28",
            time = "15:00",
            type = "Presentación",
            priority = "high"
        ),
        Event(
            id = 9,
            name = "Entrega de Documentación",
            description = "Última entrega del proyecto de ingeniería de software",
            courseId = 209,
            date = "2025-06-30",
            time = "18:00",
            type = "Proyecto",
            priority = "high"
        )
    )
}