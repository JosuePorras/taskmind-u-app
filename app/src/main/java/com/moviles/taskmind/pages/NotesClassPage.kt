package com.moviles.taskmind.pages


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moviles.taskmind.components.Header
import com.moviles.taskmind.components.NoteCard
import com.moviles.taskmind.models.Note
import com.moviles.taskmind.viewmodel.UserSessionViewModel
import com.moviles.taskmind.viewmodel.note.NoteViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesClassPage(
    modifier: Modifier = Modifier,
    userSessionViewModel: UserSessionViewModel
) {

    val notesViewModel: NoteViewModel = viewModel()
    val uiState by notesViewModel.notes.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scrollState = rememberScrollState()
    val userId = userSessionViewModel.userId.value
    LaunchedEffect(userId) {
        if (!userId.isNullOrBlank()) {
            notesViewModel.fetchNotes(userId.toInt())
        }
    }

    //Error handling
    LaunchedEffect(uiState.error) {
        uiState.error?.let{
            snackbarHostState.showSnackbar(it)
            notesViewModel.clearError()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color.White,
        topBar = {
            Header(
                title = "Mis Notas",
                buttonTitle = "Agregar",
                action = {
                    notesViewModel.clearSelectedNote()
                    showDialog = true
                }
            )
        }
    ) { paddingValues ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
        ){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(5.dp)
            ){
                if (uiState.notes.isEmpty()){
                    // Show Message when course is empty
                    Text(
                        text = "No hay notas disponibles",
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.Magenta,
                        fontSize = 18.sp
                    )
                } else {
                    // Show Notes
                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {// Reemplaza el forEach por LazyColumn
                        LazyColumn(
                            modifier = Modifier.padding(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(uiState.notes) { note ->
                                NoteCard(
                                    title = note.DSC_TITLE,
                                    course = note.Course?.DSC_NAME ?: "Sin curso",
                                    date = note.DATE_NOTE?.take(10) ?: "Sin fecha", // Formatea fecha
                                    comment = note.DSC_COMMENT,
                                    colorMain = "#FF00FF", // Color válido
                                    onEdit = { /* Implementa edición */ },
                                    onDelete = { /* Implementa eliminación */ }
                                )
                            }
                        }
                    }
                }
            }
        }

    }
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