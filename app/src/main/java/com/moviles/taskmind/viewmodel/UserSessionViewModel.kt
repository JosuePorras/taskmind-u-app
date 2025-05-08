package com.moviles.taskmind.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class UserSessionViewModel : ViewModel() {
    private val _userId = mutableStateOf<String?>(null)
    val userId: State<String?> = _userId

    fun setUserId(id: String) {
        _userId.value = id
    }

    fun clearSession() {
        _userId.value = null
    }
}