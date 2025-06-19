package com.moviles.taskmind.models

import com.google.gson.annotations.SerializedName

data class EvaluationDto(
    @SerializedName("ID_COURSE") val courseId: Int,
    @SerializedName("DSC_NAME") val name: String,
    @SerializedName("WEIGHT") val weight: Double,
    @SerializedName("DATE_EVALUATION") val date: String, // Formato esperado: "YYYY-MM-DD"
    @SerializedName("DSC_EVALUATION") val description: String,
    @SerializedName("ID_USER") val userId: Int,
    @SerializedName("SCORE_OBTAINED") val scoreObtained: Double,
    @SerializedName("DSC_COMMENT") val comment: String
)

data class EvaluationDtoResponse(
    @SerializedName("status") val statusCode: Int,
    @SerializedName("message") val message: String,
)