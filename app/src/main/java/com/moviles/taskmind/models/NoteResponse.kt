package com.moviles.taskmind.models

data class NoteResponse (
    val total: Int,
    val totalPages: Int,
    val currentPage: Int,
    val pageSize: Int,
    val notes: List<Note>
)