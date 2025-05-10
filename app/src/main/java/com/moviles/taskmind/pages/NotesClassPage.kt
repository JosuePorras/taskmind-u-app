package com.moviles.taskmind.pages

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import com.moviles.taskmind.components.course.CourseForm
import com.moviles.taskmind.viewmodel.CourseViewModel
import com.moviles.taskmind.viewmodel.UserSessionViewModel
import com.moviles.taskmind.viewmodel.note.NoteViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.log


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesClassPage(modifier: Modifier = Modifier, userSessionViewModel: UserSessionViewModel) {


    val noteViewModel: NoteViewModel = viewModel()
    //val notes by noteViewModel.notes.collectAsState()
    val uiState by noteViewModel.uiState.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    //val courseViewModel: CourseViewModel = viewModel()
    val userId = userSessionViewModel.userId.value

    Log.i("NotesClassPage", "userId: $userId")
    LaunchedEffect(userId) {
        if (!userId.isNullOrBlank()) {
            noteViewModel.loadNotes(userId)
        }
    }
//
//
//    val coroutineScope = rememberCoroutineScope()

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
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            //contentPadding = paddingValues(16.dp)
                        ) {
                            items(uiState.notes) { note ->
                                NoteCard(
                                    title = note.DSC_TITLE,
                                    course = "ejemplo",
                                    date = note.DATE_NOTE,
                                    comment = note.DSC_COMMENT,
                                    colorMain = "#4CAF50",
                                    onEdit = {/*implementar edicion*/},
                                    onDelete = {/*implementar borrado*/}
                                )

                            }
                        }
                    }
                }
            }
        }
    )
}


@Composable
private fun LoadingIndicator(modifier: Modifier = Modifier){
    CircularProgressIndicator(modifier = modifier)
}

@Composable
private fun ErrorMessage(error: String?, onRetry: () -> Unit, modifier: Modifier = Modifier){
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
private fun EmptyNotesMessage(modifier: Modifier = Modifier){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "No hay notas registradas", style = MaterialTheme.typography.bodyLarge)
    }
}
