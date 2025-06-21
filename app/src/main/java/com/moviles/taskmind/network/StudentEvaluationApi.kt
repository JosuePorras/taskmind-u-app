package com.moviles.taskmind.network

import com.moviles.taskmind.models.StudentGradeEvaluation
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface StudentEvaluationApi {
    @POST("api/student-evaluation/register")
    suspend fun registerStudentEvaluation(@Body studentEvaluation: StudentGradeEvaluation): Response<StudentGradeEvaluation>

    @PUT("api/student-evaluation/update_student_evaluation/{id}")
    suspend fun updateStudentEvaluation(@Path("id") evaluationId: Int, @Body studentEvaluation: StudentGradeEvaluation): Response<StudentGradeEvaluation>

    @DELETE("api/student-evaluation/delete_student_evaluation/{id}")
    suspend fun deleteStudentEvaluation(@Path("id") evaluationId: Int): Response<StudentGradeEvaluation>
}