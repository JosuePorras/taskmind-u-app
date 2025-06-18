package com.moviles.taskmind.viewmodel.evaluation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviles.taskmind.models.Evaluation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class EvaluationUiState(
    val isLoading: Boolean = false,
    val evaluations: List<Evaluation> = emptyList(),
    val selectedEvaluation: Evaluation? = null,
    val error: String? = null
)

class EvaluationViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(EvaluationUiState())
    val uiState: StateFlow<EvaluationUiState> = _uiState.asStateFlow()

    private val _evaluationToEdit = MutableStateFlow<Evaluation?>(null)
    val evaluationToEdit: StateFlow<Evaluation?> = _evaluationToEdit.asStateFlow()

    private val _selectedEvaluation = MutableStateFlow<Evaluation?>(null)
    val selectedEvaluation: StateFlow<Evaluation?> = _selectedEvaluation

    private val evaluationRepository = EvaluationRepository()

    fun clearSelectedNote(){
        _uiState.update { it.copy(selectedEvaluation = null) }
        _evaluationToEdit.value = null
    }

    fun selectEvaluationForEditing(evaluation: Evaluation){
        _selectedEvaluation.value = evaluation
    }
    fun clearSelectedEvaluation() {
        _selectedEvaluation.value = null
    }

    fun loadEvaluations(userId: String?){
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                val evaluations = evaluationRepository.getEvaluationsFromApi(userId)
                Log.i("EvaluationVM", "Datos Mostrados: ${evaluations.body()!!.evaluations}")
                _uiState.update {
                    it.copy(evaluations = evaluations.body()!!.evaluations, isLoading = false)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(error = "Error al cargar las evaluaciones: ${e.message}", isLoading = false)

                }
                Log.e("EvaluationVM", "Error: ${e.message}")
            }
        }
    }


    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}