package com.moviles.taskmind.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviles.taskmind.models.Course
import com.moviles.taskmind.models.CourseDto
import com.moviles.taskmind.models.GetCourseResponse
import com.moviles.taskmind.models.Professor
import com.moviles.taskmind.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CourseUiState(
    val isLoading: Boolean = false,
    val courses: List<CourseDto> = emptyList(), // Asegúrate de que este tipo coincida
    val error: String? = null
)
class CourseViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CourseUiState())
    val uiState: StateFlow<CourseUiState> get() = _uiState
    private val _professors = MutableStateFlow<List<Professor>>(emptyList())
    val professors: StateFlow<List<Professor>> get() = _professors

    init {
        fetchProfessors()
    }

    fun fetchCourses(userId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val apiResponse = RetrofitInstance.courseApi.getCourses(userId)
                Log.i("Datos mostrados", "Cursos: ${apiResponse.course}")
                _uiState.value = CourseUiState(courses = apiResponse.course)
            } catch (e: Exception) {
                Log.e("CourseViewModel", "Error al obtener cursos: ${e.message}", e)
                _uiState.value = CourseUiState(error = e.message)
            }
        }
    }

    private fun fetchProfessors() {
        viewModelScope.launch {
            try {
                _professors.value = RetrofitInstance.professorApi.getAllProfessors()
            } catch (e: Exception) {
                Log.e("CourseViewModel", "Error fetching professors: ${e.message}")
            }
        }
    }

    // CourseViewModel.kt
    fun addCourse(course: Course, professor: Professor?, selectedProfessorId: Int?) {
        viewModelScope.launch {
            try {
                // Paso 1: Obtener o crear profesor
                val finalProfessorId = selectedProfessorId ?: run {
                    if (professor == null) {
                        _uiState.value = _uiState.value.copy(error = "No se proporcionó profesor.")
                        return@launch
                    }

                    val response = try {
                        RetrofitInstance.professorApi.createProfessor(professor)
                    } catch (e: Exception) {
                        _uiState.value = _uiState.value.copy(error = "Error de conexión: ${e.message}")
                        return@launch
                    }

                    if (!response.isSuccessful) {
                        val errorMsg = response.errorBody()?.string() ?: "Error desconocido al crear profesor"
                        _uiState.value = _uiState.value.copy(error = errorMsg)
                        return@launch
                    }

                    val body = response.body()
                    if (body?.success != true || body.data == null) {
                        _uiState.value = _uiState.value.copy(error = body?.message ?: "No se pudo crear el profesor.")
                        return@launch
                    }

                    body.data.id
                }

                // Paso 2: Crear curso
                val courseToCreate = course.copy(professorId = finalProfessorId)
                val courseResponse = try {
                    RetrofitInstance.courseApi.addCourse(courseToCreate)
                } catch (e: Exception) {
                    _uiState.value = _uiState.value.copy(error = "Error de conexión al crear curso: ${e.message}")
                    return@launch
                }

                if (!courseResponse.isSuccessful) {
                    val errorMsg = courseResponse.errorBody()?.string() ?: "Error al crear curso"
                    _uiState.value = _uiState.value.copy(error = errorMsg)
                    return@launch
                }

                val courseBody = courseResponse.body()
                if (courseBody?.course == null) {
                    _uiState.value = _uiState.value.copy(error = courseBody?.message ?: "Error desconocido al crear curso.")
                    return@launch
                }


                fetchCourses(course.userId.toString())

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = "Excepción: ${e.message}")
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}
