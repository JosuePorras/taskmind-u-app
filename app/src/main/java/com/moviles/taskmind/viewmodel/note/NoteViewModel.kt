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

    fun addNote(note: Note, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                println("Enviando nota al servidor: $note")

                val response = RetrofitInstance.noteApi.addNote(note)

                println("Respuesta del servidor: ${response.code()}")
                println("Cuerpo de la respuesta: ${response.body()}")

                if (response.isSuccessful) {
                    response.body()?.let { noteResponse ->
                        if (noteResponse.success) {
                            println("Nota registrada exitosamente en el servidor")
                            loadNotes() // Recargar las notas después de agregar una nueva
                            onSuccess()
                        } else {
                            val errorMsg = "Error del servidor: ${noteResponse.message}"
                            println(errorMsg)
                            onError(errorMsg)
                        }
                    } ?: run {
                        val errorMsg = "Respuesta vacía del servidor"
                        println(errorMsg)
                        onError(errorMsg)
                    }
                } else {
                    val errorMsg = "Error HTTP: ${response.code()} - ${response.errorBody()?.string()}"
                    println(errorMsg)
                    onError(errorMsg)
                }
            } catch (e: Exception) {
                val errorMsg = "Excepción: ${e.message}"
                println(errorMsg)
                e.printStackTrace()
                onError(errorMsg)
            }
        }
    }


    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }

}