package com.moviles.taskmind.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moviles.taskmind.components.Header
import com.moviles.taskmind.components.NoteClassForm
import com.moviles.taskmind.components.NotesContainer
import com.moviles.taskmind.components.common.ConfirmationDialog
import com.moviles.taskmind.components.toast.CustomToast
import com.moviles.taskmind.viewmodel.CourseViewModel
import com.moviles.taskmind.viewmodel.UserSessionViewModel
import com.moviles.taskmind.viewmodel.note.NoteViewModel
import com.moviles.taskmind.viewmodel.toast.ToastViewModel

@Composable
fun NotesClassPage(modifier: Modifier = Modifier, userSessionViewModel: UserSessionViewModel) {
    val courseViewModel: CourseViewModel = viewModel()
    val noteViewModel: NoteViewModel = viewModel()
    val uiState by noteViewModel.uiState.collectAsState()
    val noteToEditState by noteViewModel.noteToEdit.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var toastMessage by remember { mutableStateOf<String?>(null) }
    var toastType by remember { mutableStateOf(ToastViewModel.ToastType.INFO) }

    val userId = userSessionViewModel.userId.value
    var searchText by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    var showDeleteConfirmation by remember { mutableStateOf(false) }
    var noteToDelete by remember { mutableStateOf<Int?>(null) }

    val filteredNotes = uiState.notes.filter { note ->
        note.DSC_TITLE.contains(searchText, ignoreCase = true) ||
                note.DSC_COMMENT.contains(searchText, ignoreCase = true) ||
                (note.Course?.DSC_NAME?.contains(searchText, ignoreCase = true) ?: false)
    }

    LaunchedEffect(userId) {
        if (!userId.isNullOrBlank()) {
            noteViewModel.loadNotes(userId)
            courseViewModel.fetchCourses(userId)
        }
    }

    fun handleDeleteNote(noteId: Int, userId: String?) {
        noteToDelete = noteId
        showDeleteConfirmation = true
    }

    Scaffold(
        topBar = {
            Header(
                title = "Mis Notas",
                buttonTitle = "Agregar",
                action = {
                    noteViewModel.clearSelectedNote()
                    showDialog = true
                }
            )
        },

        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when {
                    uiState.isLoading -> {
                        LoadingIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                    uiState.error != null -> {
                        ErrorMessage(
                            error = uiState.error,
                            onRetry = {
                                if (userId != null) {
                                    noteViewModel.loadNotes(userId)
                                }
                            },
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    uiState.notes.isEmpty() -> {
                        EmptyNotesMessage(modifier = Modifier.align(Alignment.Center))
                    }
                    else -> {

                        NotesContainer(
                            notes = filteredNotes,
                            onEdit = { editedNote ->
                                noteViewModel.setNoteToEdit(editedNote)
                                showDialog = true
                            },
                            onDelete = { noteIdString ->
                                val noteId = noteIdString.toIntOrNull()
                                if (noteId != null) {
                                    handleDeleteNote(noteId, userId)
                                }
                            }
                        )
                    }
                }

                if (showDialog) {
                    NoteClassForm(
                        noteViewModel = noteViewModel,
                        courseViewModel = courseViewModel,
                        userId = userId,
                        noteToEdit = noteToEditState,
                        onNoteCreated = {
                            showDialog = false
                            noteViewModel.loadNotes(userId!!)
                        },
                        onDismiss = {
                            showDialog = false
                            noteViewModel.clearSelectedNote()
                        },
                        onError = { error ->
                            toastMessage = error
                            toastType = ToastViewModel.ToastType.ERROR
                        }
                    )
                }

                toastMessage?.let { message ->
                    CustomToast(
                        message = message,
                        toastType = toastType,
                        onDismiss = { toastMessage = null }
                    )
                }

                if (showDeleteConfirmation) {
                    ConfirmationDialog(
                        title = "Eliminar Nota",
                        message = "¿Estás seguro de que deseas eliminar esta nota? Esta acción no se puede deshacer.",
                        confirmText = "Eliminar",
                        cancelText = "Cancelar",
                        confirmButtonColor = Color.Red,
                        onConfirm = {
                            noteToDelete?.let { id ->
                                noteViewModel.deleteNote(id, userId)
                                toastMessage = "Nota eliminada correctamente"
                                toastType = ToastViewModel.ToastType.SUCCESS
                            }
                            showDeleteConfirmation = false
                            noteToDelete = null
                        },
                        onDismiss = {
                            showDeleteConfirmation = false
                            noteToDelete = null
                        }
                    )
                }
            }
        }
    )
}


@Composable
private fun LoadingIndicator(modifier: Modifier = Modifier) {
    CircularProgressIndicator(modifier = modifier)
}

@Composable
private fun ErrorMessage(error: String?, onRetry: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = error ?: "Error Desconocido", color = Color.Red)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onRetry) {
            Text("Reintentar")
        }
    }
}

@Composable
private fun EmptyNotesMessage(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "No hay notas registradas", style = MaterialTheme.typography.bodyLarge)
    }
}