package com.moviles.taskmind.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moviles.taskmind.models.CourseNote
import com.moviles.taskmind.models.Note
import com.moviles.taskmind.models.NoteDto
import com.moviles.taskmind.models.UserNote
import com.moviles.taskmind.viewmodel.CourseViewModel
import com.moviles.taskmind.viewmodel.note.NoteViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteClassForm(
    noteViewModel: NoteViewModel,
    courseViewModel: CourseViewModel,
    userId: String?,
    onNoteCreated: () -> Unit,
    onDismiss: () -> Unit,
    onError: (String) -> Unit,
    noteToEdit: Note? = null
){

    val noteToEditState by noteViewModel.noteToEdit.collectAsState()
    val currentNoteToEdit = noteToEdit ?: noteToEditState

    var title by remember { mutableStateOf("") }
    var date by remember { mutableStateOf(getCurrentDate()) }
    var content by remember { mutableStateOf("") }
    var selectedCourseId by remember { mutableStateOf<Int?>(null) }

    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val context = LocalContext.current
    var showDatePicker by remember { mutableStateOf(false) }

    var courseError by remember { mutableStateOf(false) }
    var titleError by remember { mutableStateOf<String?>(null) }

    val uiState by courseViewModel.uiState.collectAsState()
    val courseList = uiState.courses
    val userIdInt = userId?.toIntOrNull() ?: 0
    var actualUserId by remember { mutableStateOf<Int?>(null) }

    var expanded by remember { mutableStateOf(false) }

    val selectedCourseName = courseList.find { it.id == selectedCourseId }?.name ?: "Selecciona un curso"


    LaunchedEffect(currentNoteToEdit) {
        if (currentNoteToEdit != null) {
            title = currentNoteToEdit.DSC_TITLE
            date = currentNoteToEdit.DATE_NOTE
            content = currentNoteToEdit.DSC_COMMENT
            selectedCourseId = currentNoteToEdit.ID_COURSE
        } else {

            title = ""
            content = ""
            date = getCurrentDate()
            selectedCourseId = null
        }
    }

    LaunchedEffect(userId) {
        if (userId != null) {
            noteViewModel.loadNotes(userId)

            val notes = noteViewModel.uiState.value.notes
            if (notes.isNotEmpty()) {
                actualUserId = notes[0].ID_USER
            } else {
                onError("No se encontró usuario con esta cédula")
            }
        }
    }

    Dialog(onDismissRequest = onDismiss, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        Card(
            modifier = Modifier.fillMaxWidth(0.95f).widthIn(max = 500.dp),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Header with title and close button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = if (currentNoteToEdit != null) "Editar Nota de clase" else "Agregar Nota de clase",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color(0xFF2BD4BD),
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Cerrar",
                            tint = Color(0xFF2BD4BD),
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Course dropdown
                Text(
                    text = "Curso",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
                )

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = selectedCourseName,
                        onValueChange = {},
                        readOnly = true,
                        placeholder = { Text("Selecciona un curso") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                            .border(
                                width = 1.dp,
                                color = if (courseError) Color.Red else Color.LightGray,
                                shape = RoundedCornerShape(50)
                            ),
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        shape = RoundedCornerShape(50),
                        isError = courseError
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        courseList.forEach { course ->
                            DropdownMenuItem(
                                text = { Text(course.name) },
                                onClick = {
                                    selectedCourseId = course.id
                                    expanded = false
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                RoundedBlueOutlinedTextField(
                    value = title,
                    onValueChange = {
                        title = it
                        titleError = null
                    },
                    labelText = "Título",
                    isError = titleError != null
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Date field with calendar icon
                Text(
                    text = "Fecha de la Clase",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
                )
                RoundedBlueOutlinedTextField(
                    value = date,
                    onValueChange = {},
                    labelText = "Fecha",
                    trailingIcon = {
                        IconButton(onClick = { showDatePicker = true }) {
                            Icon(Icons.Default.DateRange, contentDescription = null)
                        }
                    },
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Content field
                Text(
                    text = "Contenido",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
                )
                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    placeholder = { Text("Escribe tus notas aquí...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    shape = RoundedCornerShape(8.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
                Spacer(modifier = Modifier.height(24.dp))

                // Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f).height(48.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Cancelar", fontWeight = FontWeight.Bold, color = Color.Black)
                    }

                    Spacer(modifier = Modifier.size(16.dp))

                    Button(
                        onClick = {
                            if (selectedCourseId == null) {
                                courseError = true
                                onError("Selecciona un curso")
                                return@Button
                            } else {
                                courseError = false
                            }

                            var hasError = false
                            if (title.isBlank()) { titleError = "El título es obligatorio"; hasError = true }
                            if (hasError) return@Button

                            if (currentNoteToEdit?.ID_STUDENT_NOTE != null) {

                                val noteToUpdate = Note(
                                    ID_USER = actualUserId ?: 0,
                                    ID_COURSE = selectedCourseId!!,
                                    DSC_TITLE = title,
                                    DSC_COMMENT = content,
                                    DATE_NOTE = date
                                )

                                noteViewModel.updateNote(
                                    note = noteToUpdate,
                                    noteId = currentNoteToEdit.ID_STUDENT_NOTE,
                                    onSuccess = {
                                        println("Nota actualizada exitosamente")
                                        noteViewModel.clearSelectedNote()
                                        onNoteCreated()
                                    },
                                    onError = { error ->
                                        println("Error al actualizar: $error")
                                        onError(error.replace("Error del servidor: ", ""))
                                    },
                                    userId = userId
                                )
                            } else {
                                // CREAR nueva nota
                                val newNote = Note(
                                    ID_USER = userIdInt,
                                    ID_COURSE = selectedCourseId!!,
                                    DSC_TITLE = title,
                                    DSC_COMMENT = content,
                                    DATE_NOTE = date
                                )
                                println("Creando nota con userIdInt=$userIdInt (userId=$userId)")

                                noteViewModel.addNote(
                                    note = newNote,
                                    onSuccess = {
                                        println("Nota creada exitosamente")
                                        noteViewModel.clearSelectedNote()
                                        onNoteCreated()
                                    },
                                    onError = { error ->
                                        println("Error al crear: $error")
                                        onError(error.replace("Error del servidor: ", ""))
                                    },
                                    userId = userId
                                )
                            }
                        },
                        modifier = Modifier.weight(1f).height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2BD4BD))
                    ) {
                        Text(if (currentNoteToEdit != null) "Actualizar" else "Guardar", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        val calendar = Calendar.getInstance()
        if (showDatePicker) {
            android.app.DatePickerDialog(
                context,
                { _, year, month, dayOfMonth ->
                    val selectedCalendar = Calendar.getInstance()
                    selectedCalendar.set(year, month, dayOfMonth, 0, 0, 0)
                    date = formatter.format(selectedCalendar.time)
                    showDatePicker = false
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }
}

// Helper function to get current date in the format dd/MM/yyyy
private fun getCurrentDate(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return sdf.format(Date())
}

private fun formatDate(timeInMillis: Long): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return sdf.format(Date(timeInMillis))
}