package com.moviles.taskmind.components.evaluation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moviles.taskmind.viewmodel.evaluation.Evaluation
import com.moviles.taskmind.viewmodel.evaluation.EvaluationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EvaluationForm(
    viewModel: EvaluationViewModel,
    courseId: Int,
    userId: String?,
    onEvaluationCreated: () -> Unit,
    onDismiss: () -> Unit,
    onError: (String) -> Unit,
    evaluationToEdit: Evaluation? = null
) {
    val evaluationToEditState = viewModel.evaluationToEdit.collectAsState()
    val currentEvaluationToEdit = evaluationToEdit ?: evaluationToEditState

    var evaluationName by remember { mutableStateOf("") }
    var evaluationDate by remember { mutableStateOf("") }
    var courseAverage by remember { mutableStateOf(0) }
    var obtainedScore by remember { mutableStateOf(0) }

    var showDatePicker by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState()
    //val uiState by viewModel.u
    val userIdInt = userId?.toIntOrNull() ?: 0
    val actualUserId by remember { mutableStateOf<Int?>(null) }

    val expanded by remember { mutableStateOf(false) }
//    var evaluationName by remember { mutableStateOf("") }
//    var evaluationDate by remember { }

    LaunchedEffect(currentEvaluationToEdit) {
        if (currentEvaluationToEdit != null){
            evaluationName = currentEvaluationToEdit.name

        }
    }
}