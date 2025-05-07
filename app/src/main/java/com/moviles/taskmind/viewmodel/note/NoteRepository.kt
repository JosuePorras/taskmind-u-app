package com.moviles.taskmind.viewmodel.note

import com.moviles.taskmind.models.CourseNote
import com.moviles.taskmind.models.Note
import com.moviles.taskmind.models.UserNote

class NoteRepository {
    fun getNotesFromApi(): List<Note> {
        return listOf(
            Note(
                ID_STUDENT_NOTE = 1,
                ID_USER = 100,
                ID_COURSE = 20,
                DSC_TITLE = "Mi primera nota",
                DSC_COMMENT = "Este curso me encanta.",
                DATE_NOTE = "2025-05-06T19:40:28.216Z",
                User = UserNote("Ana", "López", "ana@example.com", "Ingeniería"),
                Course = CourseNote("Matemáticas Avanzadas", 50, "MAT-202")
            ),
            Note(
                ID_STUDENT_NOTE = 2,
                ID_USER = 101,
                ID_COURSE = 21,
                DSC_TITLE = "Feedback sobre proyecto",
                DSC_COMMENT = "El profesor dijo que está bien hecho.",
                DATE_NOTE = "2025-05-07T10:15:00.000Z",
                User = UserNote("Carlos", "García", "carlos@example.com", "Diseño"),
                Course = CourseNote("Diseño UX/UI", 51, "UX-101")
            )
        )
    }
}