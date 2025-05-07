package com.moviles.taskmind.components.course

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.moviles.taskmind.models.Course
import com.moviles.taskmind.models.Professor
import com.moviles.taskmind.utils.toHexString
import com.moviles.taskmind.viewmodel.CourseViewModel

@Composable
fun CourseForm(
    viewModel: CourseViewModel,
    onCourseCreated: () -> Unit,
    onDismiss: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var number by remember { mutableStateOf("") }
    var schedule by remember { mutableStateOf("") }
    var selectedColor by remember { mutableStateOf(Color(0xFF2196F3)) }

    var professorName by remember { mutableStateOf("") }
    var professorFirstName by remember { mutableStateOf("") }
    var professorLastName by remember { mutableStateOf("") }
    var professorEmail by remember { mutableStateOf("") }
    var professorPhone by remember { mutableStateOf("") }

    var useExistingProfessor by remember { mutableStateOf<Boolean?>(null) }
    var selectedProfessorId by remember { mutableStateOf<Int?>(null) }
    val professors by viewModel.professors.collectAsState()

    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Curso", "Profesor")

    val scrollState = rememberScrollState()

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .widthIn(max = 500.dp),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Box(
                modifier = Modifier
                    .background(
                        Color(0xFFFFFFFF)
                    )
            ) {
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .verticalScroll(scrollState)
                ) {
                    // Header con título y botón de cerrar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Agregar curso manualmente",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2BD4BD)
                        )
                        IconButton(
                            onClick = onDismiss,
                            modifier = Modifier
                                .size(36.dp)
                        ) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Cerrar",
                                tint = Color(0xFF2BD4BD)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Selector de pestañas mejorado
                    CourseTabSelector(tabs, selectedTabIndex) { selectedTabIndex = it }

                    Spacer(modifier = Modifier.height(16.dp))

                    if (selectedTabIndex == 0) {
                        CourseInfoTab(
                            name = name,
                            onNameChange = { name = it },
                            number = number,
                            onNumberChange = { number = it },
                            selectedColor = selectedColor,
                            onColorSelected = { selectedColor = it }
                        )
                    } else {
                        ProfessorInfoTab(
                            useExisting = useExistingProfessor,
                            onUseExistingChange = { useExistingProfessor = it },
                            professors = professors,
                            selectedProfessorId = selectedProfessorId,
                            onProfessorSelected = { selectedProfessorId = it },
                            professorName = professorName,
                            onProfessorNameChange = { professorName = it },
                            professorFirstName = professorFirstName,
                            onProfessorFirstNameChange = { professorFirstName = it },
                            professorLastName = professorLastName,
                            onProfessorLastNameChange = { professorLastName = it },
                            professorEmail = professorEmail,
                            onProfessorEmailChange = { professorEmail = it },
                            professorPhone = professorPhone,
                            onProfessorPhoneChange = { professorPhone = it }
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                    HorizontalDivider(thickness = 1.dp, color = Color(0xFFE0E0E0))
                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedButton(
                            onClick = onDismiss,
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                "Cancelar",
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF000000)
                            )
                        }

                        val currentUseExistingProfessor = useExistingProfessor ?: false
                        val currentSelectedProfessorId = selectedProfessorId

                        Button(
                            onClick = {
                                val professor = if (!currentUseExistingProfessor) {
                                    Professor(
                                        firstName = professorName,
                                        lastNameOne = professorFirstName,
                                        lastNameTwo = professorLastName,
                                        email = professorEmail,
                                        phone = professorPhone,
                                        id = 1,
                                        status = 1
                                    )
                                } else null

                                val course = Course(
                                    name = name,
                                    code = number,
                                    color = selectedColor.toHexString(),
                                    schedule = schedule,
                                    professorId = if (currentUseExistingProfessor) currentSelectedProfessorId else null,
                                    userId = 1 // usuario por defecto 1; se debería obtener desde que se inicia sesión!!
                                )

                                viewModel.addCourse(course, professor, currentSelectedProfessorId)
                                onCourseCreated()
                                onDismiss()
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF2BD4BD)
                            )
                        ) {
                            Text(
                                "Crear Curso",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}