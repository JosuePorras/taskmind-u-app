package com.moviles.taskmind.pages

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moviles.taskmind.components.CourseCard
import com.moviles.taskmind.components.course.CourseForm
import com.moviles.taskmind.viewmodel.CourseViewModel
import  com.moviles.taskmind.components.Header
import com.moviles.taskmind.viewmodel.UserSessionViewModel

@Composable
fun CoursePage(
    modifier: Modifier = Modifier,
    userSessionViewModel: UserSessionViewModel
) {
    val courseViewModel: CourseViewModel = viewModel()
    val uiState by courseViewModel.uiState.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scrollState = rememberScrollState()
    val userId = userSessionViewModel.userId.value
    LaunchedEffect(userId) {
        if (!userId.isNullOrBlank()) {
            courseViewModel.fetchCourses(userId)
        }
    }
    // Maneja la visualización de errores
    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            snackbarHostState.showSnackbar(it)
            courseViewModel.clearError()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color.White,
        topBar = {
            Header(
                title = "Cursos",
                buttonTitle = "Agregar",
                action = {
                    courseViewModel.clearSelectedCourse()
                    showDialog = true
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
        ) {

            // Aquí agregamos un contenedor para los cursos con bordes
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(5.dp) // Padding interno
            ) {
                if (uiState.courses.isEmpty()) {
                    // Mostrar mensaje si no hay cursos
                    Text(
                        text = "No hay cursos disponibles",
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.Gray,
                        fontSize = 18.sp
                    )
                } else {
                    // Mostrar los cursos dentro del contenedor
                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        uiState.courses.forEach { course ->
                            CourseCard(
                                title = course.name,
                                professor = course.professor?.let {
                                    "${it.firstName} ${it.lastNameOne} ${it.lastNameTwo}"
                                } ?: "Sin profesor asignado",
                                code = course.code,
                                progressBar = (0.15f * 100).toInt(),
                                event = "Examen Parcial",
                                colorMain = course.color,
                                onEdit = {
                                    courseViewModel.selectCourseForEditing(course)
                                    showDialog = true
                                },
                                onDelete = {
                                    // Confirmar y eliminar el curso
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    if (showDialog) {
        val selectedCourse by courseViewModel.selectedCourse.collectAsState()

        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {},
            dismissButton = {},
            text = {
                CourseForm(
                    viewModel = courseViewModel,
                    userId = userId,
                    onCourseCreated = { showDialog = false },
                    onDismiss = { showDialog = false },
                    courseToEdit = selectedCourse
                )
            }
        )
    }
}