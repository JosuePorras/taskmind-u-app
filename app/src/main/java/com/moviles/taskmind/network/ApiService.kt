package com.moviles.taskmind.network
import com.moviles.taskmind.models.Login
import com.moviles.taskmind.models.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
interface ApiService {

    @POST("api/Auth/loginAccess")
    suspend fun login(@Body login: Login): Response<LoginResponse>

}