package com.moviles.taskmind.viewmodel.note
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviles.taskmind.models.Note
import com.moviles.taskmind.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class NoteUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val notes: List<Note> = emptyList()
)

class NoteViewModel: ViewModel() {
    //Datos quemados
    private val repository = NoteRepository()
    var useMockData by mutableStateOf(true)

    private val _notes = MutableStateFlow(NoteUiState())
    val notes: StateFlow<NoteUiState> get() = _notes

    private val _selectedNote = MutableStateFlow<Note?>(null)
    val selectedNote: StateFlow<Note?> get() = _selectedNote

//    init {
//        fetchNotes()
//    }

    fun clearSelectedNote(){
        _selectedNote.value = null
    }

    fun fetchNotes(userId: Int) {
        viewModelScope.launch {
            _notes.value = _notes.value.copy(isLoading = true, error=null)
            try {
//                val notes = RetrofitInstance.noteApi.getNotesById(userId)
//                Log.i("View Data", "Notes: ${notes}")

                val notes = if (useMockData) {
                    repository.getNotesFromApi()
                } else {
                    RetrofitInstance.noteApi.getNotesById(userId)
                }
                _notes.value = _notes.value.copy(notes = notes, isLoading = false)
            } catch (e: Exception) {
                Log.e("NoteViewModel", "Error fetching notes: ${e.message}", e)
                _notes.value = _notes.value.copy(error = e.message, isLoading = false)
            }
        }
    }



    fun clearError() {
        _notes.update { it.copy(error = null) }
    }
}