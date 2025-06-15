package com.moviles.taskmind.network


import com.moviles.taskmind.models.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApi {
    @PUT("api/users/updateUser/{id}")
    suspend fun updateUser(@Path("id") userId: String, @Body user: UserResponse): Response<UserResponse>
}