package com.moviles.taskmind.network

import com.moviles.taskmind.models.Evaluation
import com.moviles.taskmind.models.EvaluationDto
import com.moviles.taskmind.models.EvaluationDtoResponse
import com.moviles.taskmind.models.GetEvaluationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface EvaluationApi {
    @GET("api/evaluation/search_evaluation")
                                        //ID_USER
    suspend fun getEvaluationsById(@Query("userId") userId: String?): Response<GetEvaluationResponse>

    @POST("api/evaluation/register")
    suspend fun addEvaluation(@Body evaluation: EvaluationDto): Response<EvaluationDtoResponse>

    @PUT("api/evaluation/update_evaluation/{id}")
    suspend fun updateEvaluation(@Path("id") evaluationId: Int, @Body evaluationDto: EvaluationDto): Response<EvaluationDtoResponse>

    @DELETE("api/evaluation/delete_evaluation/{id}")
    suspend fun deleteEvaluation(@Path("id") evaluationId: Int): Response<EvaluationDtoResponse>
}