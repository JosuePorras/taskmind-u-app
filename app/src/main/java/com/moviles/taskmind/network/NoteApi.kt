package com.moviles.taskmind.network
import com.moviles.taskmind.models.Course
import com.moviles.taskmind.models.CourseResponse
import com.moviles.taskmind.models.GetCourseResponse
import com.moviles.taskmind.models.GetNoteResponse
//import com.moviles.taskmind.models.GetNoteResponse
import com.moviles.taskmind.models.Note
import com.moviles.taskmind.models.NoteResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query


interface NoteApi {
//    @GET("api/notes/all_notes")
//    suspend fun getAllNotes(): List<Note>

    @GET("api/notes/notes_by_user_id")
    suspend fun getNotesById(@Query("userId") userId: String?): GetNoteResponse

    @POST("api/notes/register")
    suspend fun addNote(@Body note: Note): Response<NoteResponse>

    @DELETE("api/notes/delete_note/{id}")
    suspend fun deleteNote(@Path("id") noteId: Int): Response<NoteResponse>

    @PUT("api/notes/update_note/{id}")
    suspend fun updateNote(@Path("id") noteId: Int, @Body note: Note): Response<NoteResponse>

//    @GET("api/courses/notes_by_user_id")
//    suspend fun getNotesByUserId( @Query("userId") userId: String): GetNoteResponse
}