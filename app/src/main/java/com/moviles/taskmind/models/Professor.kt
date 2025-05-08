package com.moviles.taskmind.models

import com.google.gson.annotations.SerializedName

data class Professor(
    @SerializedName("ID_TEACHER") val id: Int,
    @SerializedName("DSC_FIRST_NAME") val firstName: String,
    @SerializedName("DSC_LAST_NAME_ONE") val lastNameOne: String,
    @SerializedName("DSC_LAST_NAME_TWO") val lastNameTwo: String,
    @SerializedName("DSC_EMAIL") val email: String,
    @SerializedName("DSC_PHONE") val phone: String,
    @SerializedName("STATUS") val status: Int
)