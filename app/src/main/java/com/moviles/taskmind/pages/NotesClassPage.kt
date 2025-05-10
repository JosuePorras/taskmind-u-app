package com.moviles.taskmind.pages

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesClassPage(modifier: Modifier = Modifier, userSessionViewModel: UserSessionViewModel) {
    var showDialog by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    val noteViewModel: NoteViewModel = viewModel()
    val courseViewModel: CourseViewModel = viewModel()
    val userId = userSessionViewModel.userId.value
    val notes by noteViewModel.notes.collectAsState()

    val coroutineScope = rememberCoroutineScope()

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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // Aquí mostrarías tus notas
            }

            if (showDialog) {
                NoteClassForm(
                    noteViewModel = noteViewModel,
                    courseViewModel = courseViewModel,
                    userId = userId,
                    onNoteCreated = {
                        showDialog = false
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Nota guardada exitosamente")
                        }
                    },
                    onDismiss = { showDialog = false },
                    onError = { error ->
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Error al guardar nota: $error")
                        }
                    }
                )
            }
        }
    )
}
















//
//    Box(
//        modifier = modifier
//            .fillMaxSize()
//            .background(Color.White)
//    ){
//        Column {
//            //Formulario para agregar notas
//            //Formulario para modificar notas
//            //Formulario para eliminar notas
//            //Lista de notas
//
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .verticalScroll(scrollState)
//                    .padding(horizontal = 16.dp, vertical = 8.dp),
//                verticalArrangement = Arrangement.Top,
//                horizontalAlignment = Alignment.CenterHorizontally
//
//            ){
//                //Componentes para mostrar notas [CourseNotes]
//            }
//        }
//    }
//    Column(
//        modifier = Modifier.fillMaxSize().background(Color(0xFFD76289)),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(text = "Esta es la pagina de las notas",
//            fontSize = 20.sp,
//            fontWeight = FontWeight.Bold)
//    }