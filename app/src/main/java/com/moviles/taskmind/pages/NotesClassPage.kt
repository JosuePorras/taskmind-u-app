package com.moviles.taskmind.pages

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moviles.taskmind.components.Header
import com.moviles.taskmind.components.NoteCard
import com.moviles.taskmind.components.NoteClassForm
import com.moviles.taskmind.components.NotesContainer // AGREGAR: Importar NotesContainer
import com.moviles.taskmind.components.course.CourseForm
import com.moviles.taskmind.viewmodel.CourseViewModel
import com.moviles.taskmind.viewmodel.UserSessionViewModel
import com.moviles.taskmind.viewmodel.note.NoteViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesClassPage(modifier: Modifier = Modifier, userSessionViewModel: UserSessionViewModel) {

    val noteViewModel: NoteViewModel = viewModel()
    val uiState by noteViewModel.uiState.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val courseViewModel: CourseViewModel = viewModel()
    val userId = userSessionViewModel.userId.value
    var searchText by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    var showDeleteComfirmation by remember { mutableStateOf(false) }
    var noteToDelete by remember { mutableStateOf<Int?>(null) }

    // Filtrar notas basado en el texto de búsqueda
    val filteredNotes = uiState.notes.filter { note ->
        note.DSC_TITLE.contains(searchText, ignoreCase = true) ||
                note.DSC_COMMENT.contains(searchText, ignoreCase = true) ||
                (note.Course?.DSC_NAME?.contains(searchText, ignoreCase = true) ?: false)
    }

    Log.i("NotesClassPage", "userId: $userId")
    LaunchedEffect(userId) {
        if (!userId.isNullOrBlank()) {
            noteViewModel.loadNotes(userId)
        }
    }

    fun handleDeleteNote(noteId: Int, userId: String?) {
        noteToDelete = noteId
        showDeleteComfirmation = true
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
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
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
                                println("Enviando nota al servidor: $userId")
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
                        // CAMBIO PRINCIPAL: Usar NotesContainer en lugar de LazyColumn directo
                        NotesContainer(
                            notes = uiState.notes, // Usar todas las notas (puedes cambiar a filteredNotes si tienes búsqueda)
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
                        onNoteCreated = {
                            showDialog = false
                            val message = if (noteViewModel.noteToEdit.value != null) {
                                "Nota editada correctamente"
                            } else {
                                "Nota creada correctamente"
                            }
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(message)
                            }
                        },
                        onDismiss = {
                            showDialog = false
                            noteViewModel.clearSelectedNote()
                        },
                        onError = { error ->
                            showDialog = true
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(error)
                            }
                        },
                        noteToEdit = noteViewModel.noteToEdit.value
                    )
                }

                if (showDeleteComfirmation) {
                    AlertDialog(
                        onDismissRequest = {
                            showDeleteComfirmation = false
                            noteToDelete = null
                        },
                        title = { Text(text = "Eliminar Nota") },
                        text = { Text(text = "¿Estás seguro de que deseas eliminar esta nota?") },
                        confirmButton = {
                            Button(
                                onClick = {
                                    if (noteToDelete != null) {
                                        noteViewModel.deleteNote(noteToDelete!!, userId)
                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar("Nota eliminada correctamente")
                                        }
                                    }
                                    showDeleteComfirmation = false
                                    noteToDelete = null
                                }
                            ) {
                                Text(text = "Eliminar")
                            }
                        },
                        dismissButton = {
                            Button(
                                onClick = {
                                    showDeleteComfirmation = false
                                    noteToDelete = null
                                }
                            ) {
                                Text(text = "Cancelar")
                            }
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