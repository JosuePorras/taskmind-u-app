package com.moviles.taskmind.viewmodel.note
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviles.taskmind.models.Course
import com.moviles.taskmind.models.CourseDto
import com.moviles.taskmind.models.Note
import com.moviles.taskmind.models.NoteDto
import com.moviles.taskmind.models.Professor
import com.moviles.taskmind.network.RetrofitInstance
import com.moviles.taskmind.network.RetrofitInstance.noteApi
import com.moviles.taskmind.viewmodel.CourseUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class NoteUiState(
    val isLoading: Boolean = false,
    val notes: List<NoteDto> = emptyList(),
    val selectedNote: Note? = null,
    val error: String? = null
)

class NoteViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(NoteUiState())
    val uiState: StateFlow<NoteUiState> get() = _uiState

    private val _noteToEdit = MutableStateFlow<Note?>(null)
    val noteToEdit: StateFlow<Note?> get() = _noteToEdit

    private val _selectedNote = MutableStateFlow<Note?>(null)
    val selectedNote: StateFlow<Note?> = _selectedNote

    private val noteRepository = NoteRepository()

//    init {
//        loadNotes()
//    }

    private fun NoteDto.toNote(): Note = Note(
        ID_STUDENT_NOTE = this.ID_STUDENT_NOTE,
        ID_USER = this.ID_USER,
        ID_COURSE = this.ID_COURSE,
        DSC_TITLE = this.DSC_TITLE,
        DSC_COMMENT = this.DSC_COMMENT,
        DATE_NOTE = this.DATE_NOTE
    )
    fun setNoteToEdit(noteDto: NoteDto) {
        _noteToEdit.value = noteDto.toNote()
    }
    fun clearSelectedNote() {
        _uiState.update { it.copy(selectedNote = null) }
        _noteToEdit.value = null
    }
    fun loadNotes(userId: String?) {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                val notes = noteRepository.getNotesFromApiReal(userId)
                _uiState.update {
                    it.copy(notes = notes.notes, isLoading = false)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(error = "Error al cargar notas: ${e.message}", isLoading = false)
                }
            }
        }
    }


    fun addNote(note: Note, onSuccess: () -> Unit, onError: (String) -> Unit, userId: String?) {
        viewModelScope.launch {
            try {
                println("Enviando nota al servidor: $note")
                val response = RetrofitInstance.noteApi.addNote(note)


                println("Respuesta del servidor: ${response.code()}")
                println("Cuerpo de la respuesta: ${response.body()}")

                if (response.isSuccessful) {

                    response.body()?.let { noteResponse ->
                        // Modificación clave aquí: Verificar el mensaje además del success
                        if (noteResponse.success || noteResponse.message.contains("correctamente", ignoreCase = true)) {
                            println("Nota registrada exitosamente en el servidor")
                            //_uiState.update { it.copy( isLoading = false, notes = response) }
                            loadNotes(userId)
                            onSuccess()
                        } else {
                            val errorMsg = noteResponse.message ?: "Error desconocido del servidor"
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

    fun updateNote (note: Note,noteId: Int, onSuccess: () -> Unit, onError: (String) -> Unit, userId: String?){
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.noteApi.updateNote(noteId, note)
                println("Respuesta del servidor: ${response.code()}")
                println("Cuerpo de la respuesta: ${response.body()}")

                if (response.isSuccessful){
                    response.body()?.let { noteResponse ->
                        if (noteResponse.success || noteResponse.message.contains("correctamente", ignoreCase = true)){
                            println("Nota actualizada exitosamente en el servidor")
                            //_uiState.update { it.copy( isLoading = false, notes = response) }
                            loadNotes(userId)
                            onSuccess()
                        }else {
                            val errorMsg = noteResponse.message ?: "Error desconocido del servidor"
                            println(errorMsg)
                            onError(errorMsg)
                        }

                    }?: run {
                        val errorMsg = "Respuesta vacía del servidor"
                        println(errorMsg)
                        onError(errorMsg)
                    }
                }else {
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

    fun deleteNote(noteId: Int, userId: String?) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.noteApi.deleteNote(noteId)
                if (response.isSuccessful) {
                    loadNotes(userId)
                } else {
                    _uiState.value = _uiState.value.copy(error = "Error al eliminar la nota")
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = "Excepción al eliminar: ${e.message}")
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }

}