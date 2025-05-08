package com.moviles.taskmind.network

import com.moviles.taskmind.models.Professor
import com.moviles.taskmind.models.ProfessorResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ProfessorApi {
    @POST("api/professor/register_professor")
    suspend fun createProfessor(@Body professor: Professor): Response<ProfessorResponse>

    @GET("api/professor/all_professors")
    suspend fun getAllProfessors(): List<Professor>
}