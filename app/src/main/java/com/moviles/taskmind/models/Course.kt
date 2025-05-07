package com.moviles.taskmind.models

import com.google.gson.annotations.SerializedName

// Course.kt
data class Course(
    @SerializedName("ID_COURSE") val id: Int? = null,
    @SerializedName("DSC_NAME") val name: String,
    @SerializedName("DSC_CODE") val code: String,
    @SerializedName("DSC_COLOR") val color: String,
    @SerializedName("DSC_ATTENTION") val schedule: String,
    @SerializedName("ID_TEACHER") val professorId: Int? = null,
    @SerializedName("ID_USER") val userId: Int
)