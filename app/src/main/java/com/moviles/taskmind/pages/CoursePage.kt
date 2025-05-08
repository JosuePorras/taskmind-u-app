package com.moviles.taskmind.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moviles.taskmind.components.CourseCard
import com.moviles.taskmind.components.course.CourseForm
import com.moviles.taskmind.viewmodel.CourseViewModel

@Composable
fun CoursePage(modifier: Modifier = Modifier) {
    val courseViewModel: CourseViewModel = viewModel()
    val uiState by courseViewModel.uiState.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scrollState = rememberScrollState()

    // Maneja la visualización de errores
    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            snackbarHostState.showSnackbar(it)
            courseViewModel.clearError()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
        ) {
            // Botón para abrir el formulario
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = { showDialog = true }) {
                    Text("Agregar Curso")
                }
            }

            // Lista de cursos
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CourseCard(
                    title = "Diseño y programación de plataformas móviles",
                    professor = "Prof. Rachel Bolívar Morales",
                    progressBar = (0.15f * 100).toInt(),
                    event = "Examen Parcial",
                    progressColor = "#9C27B0",
                    colorMain = "#DBEAFE"
                )

                CourseCard(
                    title = "Métodos de Investigación Científica en Informática",
                    professor = "Prof. Willy Pineda Lizano",
                    progressBar = 20,
                    event = "Avance Proyecto",
                    progressColor = "#FF9800",
                    colorMain = "#FEF3C7"
                )

                CourseCard(
                    title = "Ingenieria en Sistemas III",
                    professor = "Prof. Michael Barquero",
                    progressBar = (0.10f * 100).toInt(),
                    event = "Sprint-4",
                    progressColor = "#10B981",
                    colorMain = "#DCFCE7"
                )

                CourseCard(
                    title = "Algebra Lineal",
                    professor = "Prof. Julio Marin",
                    progressBar = 75,
                    event = "Examen Parcial II",
                    progressColor = "#EF4444",
                    colorMain = "#FEE2E2"
                )
            }
        }
    }

    // Diálogo para agregar curso
    if (showDialog) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {},
            dismissButton = {},
            text = {
                CourseForm(
                    viewModel = courseViewModel,
                    onCourseCreated = { showDialog = false },
                    onDismiss = { showDialog = false }
                )
            }
        )
    }
}