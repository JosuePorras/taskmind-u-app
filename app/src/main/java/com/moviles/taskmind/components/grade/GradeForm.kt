package com.moviles.taskmind.components.grade

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.moviles.taskmind.models.StudentGradeEvaluation

@Composable
fun GradeForm(
    evaluationId: Int,
    isVisible: Boolean,
    taskTitle: String,
    taskDate: String,
    taskPercentage: String,
    onDismiss: () -> Unit,
    onSave: (StudentGradeEvaluation) -> Unit,
    onCancel: () -> Unit
) {
    if (isVisible) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = false,
                usePlatformDefaultWidth = false
            )
        ) {
            GradeFormContent(
                evaluationId = evaluationId,
                taskTitle = taskTitle,
                taskDate = taskDate,
                taskPercentage = taskPercentage,
                onDismiss = onDismiss,
                onSave = onSave,
                onCancel = onCancel
            )
        }
    }
}

@Composable
private fun GradeFormContent(
    evaluationId: Int,
    taskTitle: String,
    taskDate: String,
    taskPercentage: String,
    onDismiss: () -> Unit,
    onSave: (StudentGradeEvaluation) -> Unit,
    onCancel: () -> Unit
) {
    var gradeValue by remember { mutableStateOf("") }
    var commentValue by remember { mutableStateOf("") }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Agregar Calificación",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2BD4BD)
                )

                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cerrar",
                        tint = Color(0xFF00BCD4)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Task info
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color(0xFFE3F2FD),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(12.dp)
            ) {
                Column {
                    Text(
                        text = taskTitle,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                    Text(
                        text = "$taskDate • $taskPercentage",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Grade input
            Text(
                text = "Calificación en la evaluación",
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = gradeValue,
                    onValueChange = { newValue ->
                        if (newValue.all { it.isDigit() || it == '.' } && newValue.length <= 5) {
                            val dotCount = newValue.count { it == '.' }
                            if (dotCount <= 1) {

                                val numericValue = newValue.toDoubleOrNull()
                                if (numericValue == null || numericValue <= 100) {
                                    gradeValue = newValue
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF00BCD4),
                        unfocusedBorderColor = Color.Gray,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    placeholder = {
                        Text(text = "0")
                    }
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "%",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Comment input
            Text(
                text = "Comentario (opcional)",
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = commentValue,
                onValueChange = {
                    if (it.length <= 200) {
                        commentValue = it
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF00BCD4),
                    unfocusedBorderColor = Color.Gray,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
                singleLine = false,
                maxLines = 3,
                shape = RoundedCornerShape(8.dp),
                placeholder = {
                    Text(
                        text = "Escribe un comentario sobre la evaluación...",
                        color = Color.Gray
                    )
                },
                supportingText = {
                    Row {
                        Text(
                            text = "${commentValue.length}/200 caracteres",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Action buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onCancel,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.Black
                    ),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        width = 1.dp
                    ),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text(
                        text = "Cancelar",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Button(
                    onClick = {
                        val numericGrade = gradeValue.toDoubleOrNull()
                        if (gradeValue.isNotEmpty() && numericGrade != null && numericGrade <= 100) {
                            val studentGrade = StudentGradeEvaluation(0,evaluationId, numericGrade, commentValue)
                            onSave(studentGrade)
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2BD4BD),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(24.dp),
                    enabled = gradeValue.isNotEmpty() && (gradeValue.toDoubleOrNull()?.let { it <= 100 } ?: false)
                ) {
                    Text(
                        text = "Guardar",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}