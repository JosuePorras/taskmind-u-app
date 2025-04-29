package  com.moviles.taskmind.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviles.taskmind.models.Login
import com.moviles.taskmind.models.LoginResponse
import com.moviles.taskmind.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

data class LoginUiState(
    val isLoading: Boolean = false,
    val userResponse: LoginResponse? = null,
    val error: String? = null
)


class LoginViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = LoginUiState(isLoading = true)

            try {

                val response = RetrofitInstance.api.login(Login(email, password))

                if (response.isSuccessful) {
                    val body = response.body()
                    Log.i("datos", response.body().toString());
                    if (body?.message is List<*>) {
                        _uiState.value = LoginUiState(error = (body.message as List<*>)[0].toString())
                    } else {
                        _uiState.value = LoginUiState(userResponse = body)
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    val message = errorBody?.let { JSONObject(it).getJSONArray("message").getString(0) }
                    _uiState.value = LoginUiState(error = message)
                }

            } catch (e: Exception) {
                _uiState.value =
                    LoginUiState(error = "Error de red o servidor: ${e.localizedMessage}")
            }
        }
    }




}
