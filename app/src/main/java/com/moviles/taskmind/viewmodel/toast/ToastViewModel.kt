package com.moviles.taskmind.viewmodel.toast


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow



class ToastViewModel : ViewModel() {
    private val _toastState = MutableStateFlow(ToastState())
    val toastState: StateFlow<ToastState> = _toastState.asStateFlow()

    fun showToast(message: String, type: ToastType) {
        _toastState.value = ToastState(show = true, message = message, type = type)
    }

    fun dismissToast() {
        _toastState.value = ToastState()
    }

    data class ToastState(
        val show: Boolean = false,
        val message: String = "",
        val type: ToastType = ToastType.INFO
    )

    enum class ToastType { INFO, SUCCESS, ERROR, WARNING }

    enum class ToastDuration(val timeMillis: Long) {
        SHORT(2000L),
        INDEFINITE(-1L)
    }
}