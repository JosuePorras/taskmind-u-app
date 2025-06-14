package  com.moviles.taskmind.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviles.taskmind.models.Login
import com.moviles.taskmind.models.LoginResponse
import com.moviles.taskmind.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

data class LoginUiState(
    val isLoading: Boolean = false,
    val userResponse: LoginResponse? = null,
    val error: String? = null
)


class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {

            _uiState.update { it.copy(isLoading = true, error = null, userResponse = null) }

            try {
                val response = RetrofitInstance.api.login(Login(email, password))

                if (response.isSuccessful) {
                    val body = response.body()
                    Log.i("LoginResponse", body.toString())

                    body?.let {
                        if (it.message is List<*>) {

                            _uiState.update { state ->
                                state.copy(
                                    isLoading = false,
                                    error = (it.message )[0].toString()
                                )
                            }
                        } else {

                            _uiState.update { state ->
                                state.copy(
                                    isLoading = false,
                                    userResponse = it
                                )
                            }
                        }
                    } ?: run {

                        _uiState.update { state ->
                            state.copy(
                                isLoading = false,
                                error = "Respuesta vacía del servidor"
                            )
                        }
                    }
                } else {

                    val errorBody = response.errorBody()?.string()
                    val message = errorBody?.let {
                        try {
                            JSONObject(it).getJSONArray("message").getString(0)
                        } catch (e: Exception) {
                            "Error al procesar la respuesta"
                        }
                    } ?: "Error desconocido"

                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            error = message
                        )
                    }
                }
            } catch (e: Exception) {

                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        error = "Error de conexión: ${e.localizedMessage ?: "Error desconocido"}"
                    )
                }
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