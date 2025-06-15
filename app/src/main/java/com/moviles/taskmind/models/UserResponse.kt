package com.moviles.taskmind.models

import com.google.gson.annotations.SerializedName



data class UserResponse(
    @SerializedName("ID_USER") val id: Int? = null,
    @SerializedName("DSC_FIRST_NAME") val name: String,
    @SerializedName("DSC_LAST_NAME_ONE") val lastName: String,
    @SerializedName("DSC_IDENTIFICATION") val ident: String,
    @SerializedName("DSC_EMAIL") val email: String,
    @SerializedName("DSC_PASSWORD") val password: String ,
    @SerializedName("CONFIRM_PASSWORD") val confirmPassword: String ,
    @SerializedName("DATE_CREATED") val date: String,
    @SerializedName("DSC_CAREER") val carrerDsc: String,
    @SerializedName("message") val message: String
)