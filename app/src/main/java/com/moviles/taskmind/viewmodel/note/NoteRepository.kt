package com.moviles.taskmind.viewmodel.note

import com.moviles.taskmind.models.CourseNote
import com.moviles.taskmind.models.GetNoteResponse
import com.moviles.taskmind.models.Note
import com.moviles.taskmind.models.NoteDto
import com.moviles.taskmind.models.UserNote
import com.moviles.taskmind.network.RetrofitInstance

class NoteRepository {
    // Datos quemados de ejemplo (simulando respuesta API)
    fun getNotesFromApi(): List<Note> {
        return listOf(
            Note(
                ID_STUDENT_NOTE = 1,
                ID_USER = 1,
                DSC_TITLE = "Tarea de Matemáticas",
                DSC_COMMENT = "Resolver ejercicios 1 al 10",
                DATE_NOTE = "2024-05-20"
            ),
            Note(
                ID_STUDENT_NOTE = 2,
                ID_USER = 1,
                DSC_TITLE = "Recordar Examen",
                DSC_COMMENT = "Estudiar capítulos 3 y 4",
                DATE_NOTE = "2024-05-22"
            )
        )
    }

    // Conexión real a API (para el futuro)
    suspend fun getNotesFromApiReal(userId: String?): GetNoteResponse {
        return RetrofitInstance.noteApi.getNotesById(userId)
    }
}