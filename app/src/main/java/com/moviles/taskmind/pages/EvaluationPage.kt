package com.moviles.taskmind.pages

import android.graphics.Paint.Align
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moviles.taskmind.components.EvaluationCard
import com.moviles.taskmind.components.Header
import com.moviles.taskmind.components.evaluation.EvaluationForm
import com.moviles.taskmind.models.CourseEvaluation
import com.moviles.taskmind.viewmodel.UserSessionViewModel
import com.moviles.taskmind.viewmodel.evaluation.EvaluationViewModel

@ExperimentalMaterial3Api
@Composable
fun EvaluationPage(
    modifier: Modifier = Modifier,
    userSessionViewModel: UserSessionViewModel
){
    val evaluationViewModel: EvaluationViewModel = viewModel()
    val uiState by evaluationViewModel.uiState.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scrollState = rememberScrollState()
    val userId = userSessionViewModel.userId.value

    Log.i("EvaluationPage", "userId: $userId")
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

    Scaffold (
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
    ){ paddingValues ->
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
                    .padding(5.dp) //internal padding
            ) {
                if (uiState.evaluations.isEmpty()) {
                    //Show message if no evaluations
                    Text(
                        text = "No hay evaluaciones disponibles",
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.Gray,
                        fontSize = 18.sp
                    )
                } else {
                    // Show evaluations inside the container
                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        uiState.evaluations.forEach {evaluation ->
                            //EvaluationCards
                            val professorName =evaluation.course.professor.firstName+ " " + evaluation.course.professor.lastNameOne + " " + evaluation.course.professor.lastNameTwo
                            EvaluationCard(
                                //title = evaluation.course,
                                //professor = evaluation.professor ?: "Sin profesor asignado",
                                courseName = evaluation.course.name,
                                professor = professorName,
                                progressBar = (0.15f * 100).toInt(),
                                colorMain = evaluation.course.color,
                                evaluation = evaluation.name,
                                onEdit = {
                                    evaluationViewModel.selectEvaluationForEditing(evaluation)
                                    showDialog = true
                                },
                                onDelete = {
                                    //
                                    //evaluationViewModel.deleteEvaluation(evaluation.id.toString(), userId ?: "")
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

        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {},
            dismissButton = {},
            text = {
                //EvaluationForm
                EvaluationForm(
                    viewModel = evaluationViewModel,
                    userId = userId,
                    onEvaluationCreated = { showDialog = false},
                    onDismiss = { showDialog = false },
                    evaluationToEdit = selectedEvaluation
                )
            }

        )
    }
}