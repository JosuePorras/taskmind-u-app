package com.moviles.taskmind.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moviles.taskmind.models.CourseNote
import com.moviles.taskmind.models.Note
import com.moviles.taskmind.models.NoteDto
import com.moviles.taskmind.models.UserNote
import com.moviles.taskmind.viewmodel.CourseViewModel
import com.moviles.taskmind.viewmodel.note.NoteViewModel
import java.text.SimpleDateFormat
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
    noteToEdit: Note? = noteViewModel.noteToEdit.value
){

    var title by remember { mutableStateOf(noteToEdit?.DSC_TITLE ?: "") }
    var date by remember { mutableStateOf(noteToEdit?.DATE_NOTE ?: getCurrentDate()) }
    var content by remember { mutableStateOf(noteToEdit?.DSC_COMMENT ?: "") }
    var selectedCourseId by remember {
        mutableStateOf<Int?>(noteToEdit?.ID_COURSE ?: null)
    }

    var course by remember { mutableStateOf("") }
//    var title by remember { mutableStateOf("") }
//    var date by remember { mutableStateOf(getCurrentDate()) }
//    var content by remember { mutableStateOf("") }
//    var selectedCourseId by remember { mutableStateOf<Int?>(null) }

    var showDatePicker by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState()
    val uiState by courseViewModel.uiState.collectAsState()
    val courseList = uiState.courses
    val userIdInt = userId?.toIntOrNull() ?: 0


    var expanded by remember { mutableStateOf(false) }

    val selectedCourseName = courseList.find { it.id == selectedCourseId }?.name ?: "Selecciona un curso"

    LaunchedEffect(key1 = noteToEdit) {
        if (noteToEdit != null) {

            title = noteToEdit.DSC_TITLE
            date = noteToEdit.DATE_NOTE
            content = noteToEdit.DSC_COMMENT
            selectedCourseId = noteToEdit.ID_COURSE
        } else {
            title = ""
            content = ""
            date = getCurrentDate()
            selectedCourseId = null
        }
    }
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp)),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
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
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (noteToEdit!= null) "Editar Nota de clase" else "Agregar Nota de clase",
                        style = MaterialTheme.typography.titleLarge
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Cerrar"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Course dropdown (simplified as text field for now)
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
                            .menuAnchor(),
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        }
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

                // Title field
                Text(
                    text = "Título",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
                )
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    placeholder = { Text("Ej: Clase 4") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Date field with calendar icon
                Text(
                    text = "Fecha de la Clase",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
                )
                OutlinedTextField(
                    value = date,
                    onValueChange = { date = it },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { showDatePicker = true }) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "Seleccionar fecha"
                            )
                        }
                    }
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

                Spacer(modifier = Modifier.height(24.dp))

                // Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Cancelar")
                    }

                    Spacer(modifier = Modifier.size(16.dp))
                    println("=== DATOS DEL FORMULARIO ===")
                    println("User ID: $userId")
                    println("Selected Course ID: $selectedCourseId")
                    println("Title: $title")
                    println("Content: $content")
                    println("Date: $date")
                    Button(

                        onClick = {
                            if (selectedCourseId == null) {
                                onError("Selecciona un curso")
                                return@Button
                            }

                            val newNote = Note(
                                ID_USER = userIdInt,
                                ID_COURSE = selectedCourseId!!,
                                DSC_TITLE = title,
                                DSC_COMMENT = content,
                                DATE_NOTE = date
                            )

                            if (noteToEdit != null && noteToEdit.ID_STUDENT_NOTE != null) {
                                noteViewModel.updateNote(
                                    note = newNote,
                                    noteId = noteToEdit.ID_STUDENT_NOTE!!, // Ahora sí es seguro
                                    onSuccess = {
                                        onNoteCreated()
                                        onDismiss()
                                    },
                                    onError = { error ->
                                        onError(error.replace("Error del servidor: ", ""))
                                    },
                                    userId = userId
                                )
                            } else {
                                noteViewModel.addNote(
                                    note = newNote,
                                    onSuccess = {

                                        title = ""
                                        content = ""
                                        selectedCourseId = null
                                        date = getCurrentDate()
                                        onNoteCreated()
                                    },
                                    onError = { error ->

                                        val cleanError = error.replace("Error del servidor: ", "")
                                        onError(cleanError)
                                    },
                                    userId = userId
                                )
                            }

                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(if(noteToEdit!= null)"Guardar cambios" else "Guardar")
                    }
                }
                }
        }

        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        datePickerState.selectedDateMillis?.let {
                            date = formatDate(it)
                        }
                        showDatePicker = false
                    }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("Cancelar")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
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
