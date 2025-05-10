package com.moviles.taskmind.network

import com.moviles.taskmind.common.Constants.API_BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    val courseApi: CourseApi by lazy {
        retrofit.create(CourseApi::class.java)
    }

    val professorApi: ProfessorApi by lazy {
        retrofit.create(ProfessorApi::class.java)
    }

    val noteApi: NoteApi by lazy {
        retrofit.create(NoteApi::class.java)
    }

    // Retrofit base
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
