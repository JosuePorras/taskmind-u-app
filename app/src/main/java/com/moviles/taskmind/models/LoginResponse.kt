package com.moviles.taskmind.models

data class LoginResponse(
    val ID_USER: String? = null,
    val DSC_FIRST_NAME: String? = null,
    val DSC_LAST_NAME_ONE: String? = null,
    val DSC_EMAIL: String? = null,
    val DSC_CAREER: String? = null,
    val message: Any? = null
)