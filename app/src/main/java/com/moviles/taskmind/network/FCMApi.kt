package com.moviles.taskmind.network

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response

data class FcmTokenRequest(
    val userId: String,
    val fcmToken: String
)

interface FCMApi {
    @POST("api/fcm/register")
    suspend fun registerFcmToken(
        @Body request: FcmTokenRequest
    ): Response<Unit>
}