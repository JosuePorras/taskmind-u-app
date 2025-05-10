package com.moviles.taskmind.network
import com.moviles.taskmind.models.Note

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface NoteApi {
//    @GET("api/notes/all_notes")
//    suspend fun getAllNotes(): List<Note>

    @GET("api/notes/notes_by_user_id")
    suspend fun getNotesById(@Query("userId") userId: Int): List<Note>

    @POST("api/notes/register")
    suspend fun createNote()
}