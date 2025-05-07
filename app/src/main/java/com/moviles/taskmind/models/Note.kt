package com.moviles.taskmind.models

data class Note (
    val ID_STUDENT_NOTE: Int,
    val ID_USER: Int,
    val ID_COURSE: Int,
    val DSC_TITLE: String,
    val DSC_COMMENT: String,
    val DATE_NOTE: String,
    val User: UserNote,
    val Course: CourseNote
)