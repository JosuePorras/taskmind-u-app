package com.moviles.taskmind.viewmodel.homepage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviles.taskmind.models.CourseStatus
import com.moviles.taskmind.models.EvualuationProx
import com.moviles.taskmind.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class HomePageUiState(
    val isLoading: Boolean = false,
    val courseStatus: CourseStatus? = null,
    val evualuationProx: List<EvualuationProx> = emptyList(),
    val error: String? = null
)

class HomePageViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomePageUiState())
    val uiState: StateFlow<HomePageUiState> = _uiState

    fun fetchHomeStatus(userId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                val response = RetrofitInstance.homepageApi.getHomeStatus(userId)

                if (response.isSuccessful && response.body() != null) {
                    val data = response.body()!!
                    //Log.i("HomePageViewModel", "Resumen: ${data.resumenCursos}, Evaluaciones: ${data.evaluacionesProximas}")
                    _uiState.value = HomePageUiState(
                        courseStatus = data.courseResumen,
                        evualuationProx = data.evaluationProx
                    )
                } else {
                    _uiState.value = HomePageUiState(error = "Error: ${response.message()}")
                }

            } catch (e: Exception) {
                //Log.e("HomePageViewModel", "Error al cargar datos de HomePage", e)
                _uiState.value = HomePageUiState(error = e.message ?: "Error desconocido")
            }
        }
    }
}