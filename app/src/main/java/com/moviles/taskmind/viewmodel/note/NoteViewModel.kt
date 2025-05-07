package com.moviles.taskmind.viewmodel.note
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviles.taskmind.models.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class NoteViewModel: ViewModel() {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: MutableStateFlow<List<Note>> = _notes

    private val noteRepository = NoteRepository()

    init {
        loadNotes()
    }

    private fun loadNotes(){
        viewModelScope.launch{
            val notesFromApi = noteRepository.getNotesFromApi()
            _notes.value = notesFromApi
        }
    }

}