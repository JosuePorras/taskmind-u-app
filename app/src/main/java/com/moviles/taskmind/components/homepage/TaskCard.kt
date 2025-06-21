package com.moviles.taskmind.components.homepage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moviles.taskmind.components.grade.GradeForm
import com.moviles.taskmind.models.StudentGradeEvaluation
import com.moviles.taskmind.viewmodel.studentevaluation.StudentEvaluationViewModel

@Composable
fun TaskCard(
    title: String,
    subtitle: String,
    date: String,
    percentage: String = "15% del curso",
    backgroundColor: Color,
    iconColor: Color,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    buttonAction:Boolean?=false,
    onEdit: () -> Unit = {},
    onDelete: () -> Unit = {},
    evaluationId: Int? = 0,
    studentEvaluationViewModel: StudentEvaluationViewModel = viewModel()
    //onAddGrade: (StudentGradeEvaluation) -> Unit = {},
    //onGradeSaved: (String) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    var showGradeForm by remember { mutableStateOf(false) }


    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(iconColor, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Text(
                    text = subtitle,
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
                Text(
                    text = date,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Box {
                if (buttonAction!=false) {
                    IconButton(onClick = { expanded = true }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Opciones",
                            tint = Color.DarkGray
                        )
                    }


                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    offset = DpOffset(0.dp, 4.dp),
                    properties = PopupProperties(focusable = true)
                ) {
                    DropdownMenuItem(
                        text = { Text("Editar evaluación") },
                        onClick = {
                            expanded = false
                            onEdit()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Eliminar evaluación") },
                        onClick = {
                            expanded = false
                            onDelete()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Agregar calificación") },
                        onClick = {
                            expanded = false
                            //onAddGrade()
                            showGradeForm = true
                        }
                    )

                }
            }
        }
    }


    GradeForm(
        evaluationId = evaluationId!!,
        isVisible = showGradeForm,
        taskTitle = title,
        taskDate = date,
        taskPercentage = percentage,
        onDismiss = { showGradeForm = false },
        onSave = { grade ->
            studentEvaluationViewModel.createStudentEvaluation(grade, onSuccess = {}, onError = {})
            //onGradeSaved(grade)
            showGradeForm = false
        },
        onCancel = { showGradeForm = false }
    )
}