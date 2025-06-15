package com.moviles.taskmind.viewmodel.evaluation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class EvaluationUiState(
    val isLoading: Boolean = false,
    val evaluations: List<Evaluation> = emptyList(),
//    val selectedEvaluation: Evaluation? = null,
    val error: String? = null
)

class EvaluationViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(EvaluationUiState())
    val uiState: StateFlow<EvaluationUiState> get() = _uiState

    private val _evaluationToEdit = MutableStateFlow<Evaluation?>(null)
    val evaluationToEdit: StateFlow<Evaluation?> get() = _evaluationToEdit

    private val _selectedEvaluation = MutableStateFlow<Evaluation?>(null)
    val selectedEvaluation: StateFlow<Evaluation?> = _selectedEvaluation

    private val evaluationRepository = EvaluationRepository()

//    fun clearSelectedNote(){
//        _uiState.update { it.copy(selectedEvaluation = null) }
//        _evaluationToEdit.value = null
//    }

    fun loadEvaluations(userId: String?){
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                val notes = evaluationRepository.getEvaluationsFromLocal()
                _uiState.update {
                    it.copy(evaluations = notes, isLoading = false)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(error = "Error al cargar notas: ${e.message}", isLoading = false)
                }

            }
        }
    }
}