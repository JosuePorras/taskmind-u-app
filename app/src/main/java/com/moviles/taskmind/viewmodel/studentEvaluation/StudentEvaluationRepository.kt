package com.moviles.taskmind.viewmodel.studentevaluation

import com.moviles.taskmind.models.StudentGradeEvaluation
import com.moviles.taskmind.network.RetrofitInstance
import retrofit2.Response

class StudentEvaluationRepository {
    suspend fun registerStudentEvaluation(studentEvaluation: StudentGradeEvaluation): Response<StudentGradeEvaluation> {
        return RetrofitInstance.studentEvaluationApi.registerStudentEvaluation(studentEvaluation)
    }

    suspend fun updateStudentEvaluation(id: Int, studentEvaluation: StudentGradeEvaluation): Response<StudentGradeEvaluation> {
        return RetrofitInstance.studentEvaluationApi.updateStudentEvaluation(id, studentEvaluation)
    }

    suspend fun deleteStudentEvaluation(evaluationId: Int): Response<StudentGradeEvaluation> {
        return RetrofitInstance.studentEvaluationApi.deleteStudentEvaluation(evaluationId)
    }
}