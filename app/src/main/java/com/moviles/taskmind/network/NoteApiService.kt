package com.moviles.taskmind.network
import com.moviles.taskmind.models.Note
import com.moviles.taskmind.models.NoteResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
interface NoteApiService {

    @POST("api/notes/register")
    suspend fun addNote(@Body note: Note): Response<NoteResponse>

}