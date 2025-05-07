package com.moviles.taskmind.models

data class NoteRequest(
    val ID_USER: Int,
    val ID_COURSE: Int,
    val DSC_TITLE: String,
    val DSC_COMMENT: String,
    val DATE_NOTE: String // format ISO 8601, example: "2025-05-07T15:17:08.850Z"
)