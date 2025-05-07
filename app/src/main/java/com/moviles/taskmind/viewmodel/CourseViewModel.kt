package com.moviles.taskmind.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviles.taskmind.models.Course
import com.moviles.taskmind.models.Professor
import com.moviles.taskmind.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CourseUiState(
    val isLoading: Boolean = false,
    val courses: List<Course> = emptyList(),
    val error: String? = null
)

class CourseViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CourseUiState())
    val uiState: StateFlow<CourseUiState> get() = _uiState
    private val _professors = MutableStateFlow<List<Professor>>(emptyList())
    val professors: StateFlow<List<Professor>> get() = _professors

    init {
        fetchCourses()
        fetchProfessors()
    }

    fun fetchCourses() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val apiCourses = RetrofitInstance.courseApi.getCourses()
                _uiState.value = CourseUiState(courses = apiCourses)
            } catch (e: Exception) {
                Log.e("CourseViewModel", "Error fetching courses: ${e.message}", e)
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

                // Curso creado exitosamente
                fetchCourses()

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = "Excepción: ${e.message}")
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}
