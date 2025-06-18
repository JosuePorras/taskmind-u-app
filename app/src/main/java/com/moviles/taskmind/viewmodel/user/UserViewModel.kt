package com.moviles.taskmind.viewmodel.user

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.moviles.taskmind.models.UserResponse
import com.moviles.taskmind.network.RetrofitInstance
import com.moviles.taskmind.viewmodel.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject


data class UserUiState(
    val isLoading: Boolean = false,
    val userResponse: UserResponse? = null,
    val error: String? = null
)
data class ZodError(
    val code: String,
    val message: String,
    val path: List<String>
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
                    val errorBodyString = response.errorBody()?.string()

                    val errorMessage = try {
                        val json = errorBodyString?.let { JSONObject(it) }
                        when (val message = json?.get("message")) {
                            is JSONArray -> message.getString(0)
                            is String -> message
                            else -> "Error desconocido"
                        }
                    } catch (e: Exception) {
                        "Error desconocido: ${response.code()} - ${response.message()}"
                    }

                    Log.i("ErrorBackend", errorMessage)

                    _uiState.value = UserUiState(
                        isLoading = false,
                        userResponse = null,
                        error = errorMessage
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

    fun addUser(user: UserResponse) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            try {
                val response = RetrofitInstance.userApi.registerUser(user)

                if (response.isSuccessful && response.body() != null) {
                    _uiState.value = UserUiState(
                        isLoading = false,
                        userResponse = response.body(),
                        error = null
                    )
                } else {
                    val errorBodyString = response.errorBody()?.string()

                    val errorMessage = try {
                        val json = errorBodyString?.let { JSONObject(it) }
                        when (val message = json?.get("message")) {
                            is JSONArray -> message.getString(0)
                            is String -> message
                            else -> "Error desconocido"
                        }
                    } catch (e: Exception) {
                        "Error desconocido: ${response.code()} - ${response.message()}"
                    }

                    Log.i("ErrorBackend", errorMessage)

                    _uiState.value = UserUiState(
                        isLoading = false,
                        userResponse = null,
                        error = errorMessage
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

