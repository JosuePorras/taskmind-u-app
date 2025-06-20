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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun GradeForm(
    isVisible: Boolean,
    taskTitle: String,
    taskDate: String,
    taskPercentage: String,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit,
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
    taskTitle: String,
    taskDate: String,
    taskPercentage: String,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit,
    onCancel: () -> Unit
) {
    var gradeValue by remember { mutableStateOf("") }

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


            Text(
                text = "Porcentaje obtenido en la evaluación",
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

                        if (newValue.all { it.isDigit() } && newValue.length <= 3) {
                            gradeValue = newValue
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF00BCD4),
                        unfocusedBorderColor = Color.Gray,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "%",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(24.dp))


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
                        if (gradeValue.isNotEmpty()) {
                            onSave(gradeValue)
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
                    enabled = gradeValue.isNotEmpty()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Guardar",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                    }
                }
            }
        }
    }
}