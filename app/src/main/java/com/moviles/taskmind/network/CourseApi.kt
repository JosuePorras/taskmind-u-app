package com.moviles.taskmind.network

import com.moviles.taskmind.models.Course
import com.moviles.taskmind.models.CourseResponse
import com.moviles.taskmind.models.GetCourseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface CourseApi {
    @GET("api/courses/all_course")
    suspend fun getCourses( @Query("userId") userId: String): GetCourseResponse

    @POST("api/courses/register")
    suspend fun addCourse(@Body course: Course): Response<CourseResponse>

    @PUT("api/courses/update_course/{id}")
    suspend fun updateCourse(@Path("id") courseId: String, @Body course: Course): Response<CourseResponse>

    @DELETE("api/courses/delete_course/{id}")
    suspend fun deleteCourse(@Path("id") courseId: String): Response<CourseResponse>
}