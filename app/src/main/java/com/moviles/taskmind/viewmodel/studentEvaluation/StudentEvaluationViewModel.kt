package com.moviles.taskmind.viewmodel.studentevaluation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviles.taskmind.models.StudentGradeEvaluation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class StudentEvaluationUiState(
    val isLoading: Boolean = false,
    val studentEvaluations: List<StudentGradeEvaluation> = emptyList(),
    val selectedStudentEvaluation: StudentGradeEvaluation? = null,
    val error: String? = null
)

class StudentEvaluationViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(StudentEvaluationUiState())
    val uiState: StateFlow<StudentEvaluationUiState> = _uiState.asStateFlow()

    private val _selectedStudentEvaluation = MutableStateFlow<StudentGradeEvaluation?>(null)
    val selectedStudentEvaluation: StateFlow<StudentGradeEvaluation?> = _selectedStudentEvaluation

    private val studentEvaluationRepository = StudentEvaluationRepository()

    fun clearSelectedStudentEvaluation() {
        _selectedStudentEvaluation.value = null
    }

    fun selectStudentEvaluationForEditing(studentEvaluation: StudentGradeEvaluation) {
        _selectedStudentEvaluation.value = studentEvaluation
    }

    fun createStudentEvaluation(
        studentEvaluation: StudentGradeEvaluation,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = studentEvaluationRepository.registerStudentEvaluation(studentEvaluation)
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onError("Error al crear evaluación del estudiante: ${response.message()}")
                }
            } catch (e: Exception) {
                onError("Error de red: ${e.message}")
                Log.e("StudentEvaluationVM", "Error: ${e.message}")
            }
        }
    }

    fun updateStudentEvaluation(
        id: Int,
        studentEvaluation: StudentGradeEvaluation,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = studentEvaluationRepository.updateStudentEvaluation(id, studentEvaluation)
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onError("Error al actualizar evaluación del estudiante: ${response.message()}")
                }
            } catch (e: Exception) {
                onError("Error de red: ${e.message}")
                Log.e("StudentEvaluationVM", "Error: ${e.message}")
            }
        }
    }

    fun deleteStudentEvaluation(
        evaluationId: Int,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = studentEvaluationRepository.deleteStudentEvaluation(evaluationId)
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onError("Error al eliminar evaluación del estudiante: ${response.message()}")
                }
            } catch (e: Exception) {
                onError("Error de red: ${e.message}")
                Log.e("StudentEvaluationVM", "Error: ${e.message}")
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}