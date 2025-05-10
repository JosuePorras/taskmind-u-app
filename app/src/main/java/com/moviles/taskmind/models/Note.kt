package com.moviles.taskmind.models

import com.google.gson.annotations.SerializedName

data class Note (
    @SerializedName("ID_STUDENT_NOTE") val ID_STUDENT_NOTE: Int? = null,
    @SerializedName("ID_USER") val ID_USER: Int,
    @SerializedName("ID_COURSE") val ID_COURSE: Int? = null,
    @SerializedName("DSC_TITLE") val DSC_TITLE: String,
    @SerializedName("DSC_COMMENT") val DSC_COMMENT: String,
    @SerializedName("DATE_NOTE") val DATE_NOTE: String,

)

data class NoteDto(
    @SerializedName("ID_STUDENT_NOTE") val ID_STUDENT_NOTE: Int,
    @SerializedName("ID_USER") val ID_USER: Int,
    @SerializedName("ID_COURSE") val ID_COURSE: Int,
    @SerializedName("DSC_TITLE") val DSC_TITLE: String,
    @SerializedName("DSC_COMMENT") val DSC_COMMENT: String,
    @SerializedName("DATE_NOTE") val DATE_NOTE: String,
    @SerializedName("User") val User: UserNote? = null,
    @SerializedName("Course") val Course: CourseNote? = null
)