package com.moviles.taskmind.viewmodel.evaluation

import com.moviles.taskmind.models.GetEvaluationResponse
import com.moviles.taskmind.network.RetrofitInstance
import retrofit2.Response


class EvaluationRepository {
    suspend fun getEvaluationsFromApi(userId: String?): Response<GetEvaluationResponse> {
        return RetrofitInstance.evaluationApi.getEvaluationsById(userId)
    }
}