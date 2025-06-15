package com.moviles.taskmind.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moviles.taskmind.viewmodel.UserSessionViewModel
import com.moviles.taskmind.viewmodel.evaluation.EvaluationViewModel

@Composable
fun EvaluationPage(
    modifier: Modifier = Modifier,
    userSessionViewModel: UserSessionViewModel
){
    val evaluationViewModel: EvaluationViewModel = viewModel()
    val uiState by evaluationViewModel.uiState.collectAsState()

}