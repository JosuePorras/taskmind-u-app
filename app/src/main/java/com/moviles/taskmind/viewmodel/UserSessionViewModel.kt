package com.moviles.taskmind.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class UserSessionViewModel : ViewModel() {
    private val _userId = mutableStateOf<String?>(null)
    val userId: State<String?> = _userId

    private val _name = mutableStateOf<String?>(null)
    val userName: State<String?> = _name

    private val _secName = mutableStateOf<String?>(null)
    val userSecName: State<String?> = _secName

    private val _carrer = mutableStateOf<String?>(null)
    val userCarrer: State<String?> = _carrer

    private val _email = mutableStateOf<String?>(null)
    val userEmail: State<String?> = _email

    fun setUserId(id: String) {
        _userId.value = id
    }

    fun setUserName(name: String) {
        _name.value = name
    }

    fun setSecName(secName: String) {
        _secName.value = secName
    }

    fun setCareer(career: String) {
        _carrer.value = career
    }

    fun setEmail(email: String) {
        _email.value = email
    }

    fun clearSession() {
        _userId.value = null
    }
}