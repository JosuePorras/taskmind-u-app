package com.moviles.taskmind.models

import com.google.gson.annotations.SerializedName

data class StudentGradeEvaluation(
    @SerializedName("ID_EVALUATION") val evaluationId: Int,
    @SerializedName("ID_TYPE") val typeId: Int,
    @SerializedName("SCORE_OBTAINED") val scoreObtained: Double,
    @SerializedName("DSC_COMMENT") val comment: String?
)