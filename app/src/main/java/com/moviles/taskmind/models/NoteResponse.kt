package com.moviles.taskmind.models

import com.google.gson.annotations.SerializedName

data class NoteResponse(
    @SerializedName("status") val statusCode: Int,
    @SerializedName("message") val message: String,
    @SerializedName("note") val note: Note?,
    val success: Boolean
)

data class GetNoteResponse(
    @SerializedName("notes") val notes: List<NoteDto>
)