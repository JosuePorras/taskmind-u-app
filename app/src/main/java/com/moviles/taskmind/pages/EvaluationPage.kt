package com.moviles.taskmind.pages

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moviles.taskmind.components.Header
import com.moviles.taskmind.components.evaluation.EvaluationCard
import com.moviles.taskmind.components.evaluation.EvaluationForm
import com.moviles.taskmind.components.evaluation.EvaluationItem
import com.moviles.taskmind.viewmodel.UserSessionViewModel
import com.moviles.taskmind.viewmodel.evaluation.EvaluationViewModel

@Composable
fun EvaluationPage(
    modifier: Modifier = Modifier,
    userSessionViewModel: UserSessionViewModel
) {
    val evaluationViewModel: EvaluationViewModel = viewModel()
    val uiState by evaluationViewModel.uiState.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scrollState = rememberScrollState()
    val userId = userSessionViewModel.userId.value

    LaunchedEffect(userId) {
        if (!userId.isNullOrBlank()) {
            evaluationViewModel.loadEvaluations(userId)
        }
    }

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            snackbarHostState.showSnackbar(it)
            evaluationViewModel.clearError()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color.White,
        topBar = {
            Header(
                title = "Mis Evaluaciones",
                subtitle = "Gestiona tus evaluaciones",
                buttonTitle = "Agregar",
                action = {
                    evaluationViewModel.clearSelectedEvaluation()
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(5.dp) // Padding interno del contenedor
            ) {
                if (uiState.evaluations.isEmpty()) {
                    Text(
                        text = "No hay evaluaciones disponibles",
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.Gray,
                        fontSize = 18.sp
                    )
                } else {
                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val groupedEvaluations = uiState.evaluations.groupBy { it.course.id }

                        groupedEvaluations.forEach { (_, courseEvaluations) ->
                            val course = courseEvaluations.first().course
                            val professorName = "${course.professor.firstName} ${course.professor.lastNameOne} ${course.professor.lastNameTwo}"

                            EvaluationCard(
                                courseName = course.name,
                                professor = professorName,
                                progressBar = 50,
                                colorMain = course.color,
                                evaluations = courseEvaluations.map { eval ->
                                    EvaluationItem(
                                        title = eval.name,
                                        subtitle = eval.description,
                                        date = dateFormat(eval.date),
                                        icon = Icons.Default.Book
                                    )
                                },
                                onEdit = {
                                    evaluationViewModel.selectEvaluationForEditing(courseEvaluations.first())
                                    showDialog = true
                                },
                                onDelete = {
                                    evaluationViewModel.selectEvaluationForEditing(courseEvaluations.first())
                                    showDialog = true
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    if (showDialog) {
        val selectedEvaluation by evaluationViewModel.selectedEvaluation.collectAsState()

        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {},
            dismissButton = {},
            text = {
                EvaluationForm(
                    viewModel = evaluationViewModel,
                    userId = userId,
                    onEvaluationCreated = { showDialog = false },
                    onDismiss = { showDialog = false },
                    evaluationToEdit = selectedEvaluation
                )
            }
        )
    }
}