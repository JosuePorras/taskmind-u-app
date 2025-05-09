package com.moviles.taskmind.network
import com.moviles.taskmind.models.Note

import retrofit2.http.GET
import retrofit2.http.POST


interface NoteApi {
    @GET("api/notes/all_notes")
    suspend fun getAllNotes(): List<Note>

    //@GET("api/notes/getById/{id}")    : Endpoint for get notes by id
    //suspend fun getNotesById(@Path("id") id: Int): List<Note>

    @POST("api/notes/register")
    suspend fun createNote()
}