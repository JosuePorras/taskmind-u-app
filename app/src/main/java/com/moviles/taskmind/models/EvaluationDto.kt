package com.moviles.taskmind.models

import com.google.gson.annotations.SerializedName

data class EvaluationDto(
    @SerializedName("ID_COURSE") val courseId: Int,
    @SerializedName("DSC_NAME") val name: String,
    @SerializedName("WEIGHT") val weight: Double,
    @SerializedName("DATE_EVALUATION") val date: String,
    @SerializedName("DSC_EVALUATION") val description: String,
    @SerializedName("ID_USER") val userId: Int,
)

data class EvaluationDtoResponse(
    @SerializedName("status") val statusCode: Int,
    @SerializedName("message") val message: String,
)