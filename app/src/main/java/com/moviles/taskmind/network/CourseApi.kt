package com.moviles.taskmind.network

import com.moviles.taskmind.models.Course
import com.moviles.taskmind.models.CourseResponse
import com.moviles.taskmind.models.GetCourseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CourseApi {
    @GET("api/courses/all_course")
    suspend fun getCourses( @Query("userId") userId: String): GetCourseResponse

    @POST("api/courses/register")
    suspend fun addCourse(@Body course: Course): Response<CourseResponse>
}