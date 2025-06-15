package com.moviles.taskmind.viewmodel.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviles.taskmind.models.UserResponse
import com.moviles.taskmind.network.RetrofitInstance
import com.moviles.taskmind.viewmodel.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class UserUiState(
    val isLoading: Boolean = false,
    val userResponse: UserResponse? = null,
    val error: String? = null
)

class UserViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UserUiState())
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    fun updateUser(user: UserResponse) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            try {
                val response = RetrofitInstance.userApi.updateUser(user.ident, user)

                if (response.isSuccessful && response.body() != null) {
                    _uiState.value = UserUiState(
                        isLoading = false,
                        userResponse = response.body(),
                        error = null
                    )
                } else {
                    _uiState.value = UserUiState(
                        isLoading = false,
                        userResponse = null,
                        error = "Error: ${response.code()} - ${response.message()}"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = UserUiState(
                    isLoading = false,
                    userResponse = null,
                    error = e.localizedMessage ?: "Error desconocido"
                )
            }
        }
    }


    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }

    fun clearUserResponse() {
        _uiState.update { it.copy(userResponse = null) }
    }

}

