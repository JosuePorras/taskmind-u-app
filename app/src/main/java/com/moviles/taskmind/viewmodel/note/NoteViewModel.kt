package com.moviles.taskmind.viewmodel.note
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviles.taskmind.models.Course
import com.moviles.taskmind.models.CourseDto
import com.moviles.taskmind.models.Note
import com.moviles.taskmind.models.NoteDto
import com.moviles.taskmind.models.Professor
import com.moviles.taskmind.network.RetrofitInstance
import com.moviles.taskmind.viewmodel.CourseUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class NoteUiState(
    val isLoading: Boolean = false,
    val notes: List<NoteDto> = emptyList(), // Asegúrate de que este tipo coincida
    val error: String? = null
)

class NoteViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(NoteUiState())
    val uiState: StateFlow<NoteUiState> get() = _uiState
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: MutableStateFlow<List<Note>> = _notes
    private val _selectedNote = MutableStateFlow<NoteDto?>(null)
    val selectedNote: StateFlow<NoteDto?> get() = _selectedNote

    private val noteRepository = NoteRepository()

    init {
        loadNotes()
    }

    fun clearSelectedNote() {
        _selectedNote.value = null
    }
    private fun loadNotes(){
        viewModelScope.launch{
            val notesFromApi = noteRepository.getNotesFromApi()
            _notes.value = notesFromApi
        }
    }

    fun addNote(note: Note) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.noteApi.addNote(note)
                if (response.isSuccessful) {

                    val responseBody = response.body()
                    if (responseBody != null && responseBody.success) {

                    } else {
                        _uiState.value = _uiState.value.copy(error = responseBody?.message ?: "Error desconocido al agregar nota")
                    }
                } else {
                    _uiState.value = _uiState.value.copy(error = "Error al agregar la nota: ${response.code()}")
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = "Excepción al agregar nota: ${e.message}")
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }

}