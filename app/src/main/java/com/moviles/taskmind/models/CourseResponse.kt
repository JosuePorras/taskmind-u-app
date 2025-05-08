package com.moviles.taskmind.models

import com.google.gson.annotations.SerializedName

data class CourseResponse(
    @SerializedName("status") val statusCode: Int,
    @SerializedName("message") val message: String,
    @SerializedName("course") val course: Course?
)