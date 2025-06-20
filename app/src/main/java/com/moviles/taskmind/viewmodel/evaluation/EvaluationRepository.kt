package com.moviles.taskmind.viewmodel.evaluation

import com.moviles.taskmind.models.EvaluationDto
import com.moviles.taskmind.models.EvaluationDtoResponse
import com.moviles.taskmind.models.GetEvaluationResponse
import com.moviles.taskmind.network.RetrofitInstance
import retrofit2.Response


class EvaluationRepository {
    suspend fun getEvaluationsFromApi(userId: String?): Response<GetEvaluationResponse> {
        return RetrofitInstance.evaluationApi.getEvaluationsById(userId)
    }

    suspend fun addEvaluation(evaluationDto: EvaluationDto): Response<EvaluationDtoResponse> {
        return RetrofitInstance.evaluationApi.addEvaluation(evaluationDto)
    }

    suspend fun updateEvaluation(id: Int, evaluationDto: EvaluationDto): Response<EvaluationDtoResponse> {
        return RetrofitInstance.evaluationApi.updateEvaluation(id, evaluationDto)
    }

    suspend fun deleteEvaluation(evaluationId: Int): Response<EvaluationDtoResponse> {
        return RetrofitInstance.evaluationApi.deleteEvaluation(evaluationId)
    }

}