package com.moviles.taskmind.network
import com.moviles.taskmind.models.Course
import com.moviles.taskmind.models.CourseResponse
import com.moviles.taskmind.models.Note
import com.moviles.taskmind.models.NoteResponse
import retrofit2.Response
import retrofit2.http.Body

import retrofit2.http.GET
import retrofit2.http.POST


interface NoteApi {
    @GET("api/notes/all_notes")
    suspend fun getAllNotes(): List<Note>

    //@GET("api/notes/getById/{id}")    : Endpoint for get notes by id
    //suspend fun getNotesById(@Path("id") id: Int): List<Note>

    @POST("api/notes/register")
    suspend fun addNote(@Body note: Note): Response<NoteResponse>
}