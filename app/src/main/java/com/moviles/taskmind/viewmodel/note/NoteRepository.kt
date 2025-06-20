package com.moviles.taskmind.viewmodel.note

import com.moviles.taskmind.models.CourseNote
import com.moviles.taskmind.models.GetNoteResponse
import com.moviles.taskmind.models.Note
import com.moviles.taskmind.models.NoteDto
import com.moviles.taskmind.models.UserNote
import com.moviles.taskmind.network.RetrofitInstance

class NoteRepository {
    suspend fun getNotesFromApiReal(userId: String?): List<NoteDto> {
        val response = RetrofitInstance.noteApi.getNotesById(userId)
        if (response.isSuccessful) {
            return response.body()?.notes ?: emptyList()
        } else {
            throw Exception("Error ${response.code()}: ${response.message()}")
        }
    }
}