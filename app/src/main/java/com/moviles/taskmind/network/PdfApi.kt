package com.moviles.taskmind.network

import com.moviles.taskmind.models.CourseResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface PdfApi {
    @Multipart
    @POST("/api/pdf/registerPDF/{id}")
    suspend fun uploadPdf(
        @Path("id") id: String,
        @Part pdf: MultipartBody.Part
    ): Response<CourseResponse>

}