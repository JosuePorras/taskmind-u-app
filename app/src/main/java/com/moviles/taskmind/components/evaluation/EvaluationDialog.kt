package com.moviles.taskmind.components.evaluation

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.moviles.taskmind.components.homepage.TaskCard
import com.moviles.taskmind.models.CourseDto
import com.moviles.taskmind.models.Evaluation
import com.moviles.taskmind.pages.dateFormat

@Composable
fun EvaluationDialog(
    evaluation: List<Evaluation>? = null,
    course:String,
    backgroundColor: Color,
    iconColor: Color,
    onDismiss: () -> Unit,
    onSave: (List<Evaluation>?) -> Unit
) {
    var evalList by remember { mutableStateOf(evaluation ?: emptyList()) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(text = "Evaluaciones")
        },
        text = {
            Column {
                if (evalList.isEmpty()) {
                    Text("No hay evaluaciones registradas.")
                } else {
                    evalList.forEach {eval->
                        TaskCard(
                            title = eval.name,
                            subtitle = course,
                            date = dateFormat(eval.date),
                            icon = Icons.Default.Book,
                            backgroundColor = backgroundColor,
                            iconColor = iconColor,
                            onAddGrade = {},
                            onEdit = {},
                            onDelete = {}
                        )
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Cancelar")
            }
        }
    )
}
