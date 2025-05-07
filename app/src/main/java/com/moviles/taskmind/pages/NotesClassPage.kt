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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moviles.taskmind.components.NoteCard
import com.moviles.taskmind.viewmodel.note.NoteViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesClassPage(modifier: Modifier = Modifier) {
//    val scrollState = rememberScrollState()
//    val viewModel: NoteViewModel = viewModel()
//    val notes by viewModel.notes.collectAsState()

    val viewModel: NoteViewModel = viewModel()
    val notes by viewModel.notes.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Mis Notas") })
        },
        content = { paddingValues ->
            // 👇 Aquí usamos LazyColumn directamente (ya es scrollable)
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(items = notes, key = { it.ID_STUDENT_NOTE }) { note ->
                    NoteCard(note = note)
                }
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