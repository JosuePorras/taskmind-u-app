package com.moviles.taskmind.components.evaluation

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.moviles.taskmind.components.RoundedBlueOutlinedTextField
import com.moviles.taskmind.models.Evaluation
import com.moviles.taskmind.models.EvaluationDto
import com.moviles.taskmind.viewmodel.CourseViewModel
import com.moviles.taskmind.viewmodel.evaluation.EvaluationViewModel
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("DefaultLocale")
@Composable
fun EvaluationForm(
    viewModel: EvaluationViewModel,
    courseViewModel: CourseViewModel,
    userId: String?,
    onEvaluationCreated: () -> Unit,
    onDismiss: () -> Unit,
    evaluationToEdit: Evaluation? = null
) {
    val scrollState = rememberScrollState()
    val courses by courseViewModel.uiState.collectAsState()
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val formatterTime = SimpleDateFormat("HH:mm", Locale.getDefault())

    var name by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf("") }
    var selectedCourseId by remember { mutableStateOf<Int?>(null) }
    var weight by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }

    var nameError by remember { mutableStateOf<String?>(null) }
    var typeError by remember { mutableStateOf<String?>(null) }
    var courseError by remember { mutableStateOf<String?>(null) }
    var dateError by remember { mutableStateOf<String?>(null) }
    var timeError by remember { mutableStateOf<String?>(null) }
    var weightError by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    val evaluationTypes = listOf("Tarea", "Examen", "Proyecto", "Presentación", "Laboratorio")
    val evaluationColors = mapOf(
        "Tarea" to Color(0xFFABECBE), // green
        "Examen" to Color(0xFFC8ABFC), // purple
        "Proyecto" to Color(0xFFA0C6FD), // blue
        "Presentación" to Color(0xFFFF7B6F), // red
        "Laboratorio" to Color(0xFFF6EBA0) // yellow
    )

    LaunchedEffect(Unit) {
        if (evaluationToEdit != null) {
            name = evaluationToEdit.name
            selectedType = evaluationToEdit.description
            weight = evaluationToEdit.weight.toString()
            selectedCourseId = evaluationToEdit.course.id
            val parsedDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).parse(evaluationToEdit.date)
            parsedDate?.let {
                date = formatter.format(it)
                time = formatterTime.format(it)
            }
        }
        userId?.let { courseViewModel.fetchCourses(it) }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .widthIn(max = 500.dp),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Box(modifier = Modifier.background(Color.White)) {
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .verticalScroll(scrollState)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (evaluationToEdit != null) "Editar Evaluación" else "Nueva Evaluación",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2BD4BD)
                        )
                        IconButton(onClick = onDismiss) {
                            Icon(Icons.Default.Close, contentDescription = "Cerrar", tint = Color(0xFF2BD4BD))
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    if (nameError != null) Text(nameError!!, color = Color.Red)
                    RoundedBlueOutlinedTextField(
                        value = name,
                        onValueChange = {
                            name = it
                            nameError = null
                        },
                        labelText = "Título",
                        isError = nameError != null
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    if (courseError != null) Text(courseError!!, color = Color.Red)
                    val selectedCourseName = courses.courses.find { it.id == selectedCourseId }?.name ?: "Seleccionar curso"
                    var expanded by remember { mutableStateOf(false) }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, Color.LightGray, RoundedCornerShape(50.dp))
                            .clickable { expanded = true }
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = selectedCourseName,
                                modifier = Modifier.weight(1f)
                            )
                            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                        }

                        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                            courses.courses.forEach { course ->
                                DropdownMenuItem(
                                    text = { Text(course.name) },
                                    onClick = {
                                        selectedCourseId = course.id
                                        courseError = null
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    if (dateError != null) Text(dateError!!, color = Color.Red)
                    RoundedBlueOutlinedTextField(
                        value = date,
                        onValueChange = {},
                        labelText = "Fecha",
                        trailingIcon = {
                            IconButton(onClick = { showDatePicker = true }) {
                                Icon(Icons.Default.DateRange, contentDescription = null)
                            }
                        },
                        isError = dateError != null
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    if (timeError != null) Text(timeError!!, color = Color.Red)
                    RoundedBlueOutlinedTextField(
                        value = time,
                        onValueChange = {},
                        labelText = "Hora",
                        trailingIcon = {
                            IconButton(onClick = { showTimePicker = true }) {
                                Icon(Icons.Default.AccessTime, contentDescription = null)
                            }
                        },
                        isError = timeError != null
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    if (weightError != null) Text(weightError!!, color = Color.Red)
                    RoundedBlueOutlinedTextField(
                        value = weight,
                        onValueChange = {
                            weight = it.filter { c -> c.isDigit() || c == '.' }
                            weightError = null
                        },
                        labelText = "Porcentaje de evaluación (%)",
                        isError = weightError != null,
                        keyboardType = KeyboardType.Number
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text("Tipo de Evaluación", fontWeight = FontWeight.Medium)
                    if (typeError != null) Text(typeError!!, color = Color.Red)

                    val itemsPerRow = 2
                    val typeChunks = evaluationTypes.chunked(itemsPerRow)

                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        typeChunks.forEach { rowItems ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                rowItems.forEach { type ->
                                    val isSelected = selectedType == type
                                    val color = evaluationColors[type] ?: Color.Gray
                                    Button(
                                        onClick = {
                                            selectedType = type
                                            typeError = null
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (isSelected) color else Color.White,
                                            contentColor = if (isSelected) Color.White else Color.Black
                                        ),
                                        shape = RoundedCornerShape(50),
                                        border = BorderStroke(1.dp, if (isSelected) color else Color.LightGray),
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(36.dp),
                                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                                    ) {
                                        Text(type, style = MaterialTheme.typography.bodySmall)
                                    }
                                }
                                repeat(itemsPerRow - rowItems.size) {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        OutlinedButton(
                            onClick = onDismiss,
                            modifier = Modifier.weight(1f).height(48.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Cancelar", fontWeight = FontWeight.Bold, color = Color.Black)
                        }

                        Button(
                            onClick = {

                                var hasError = false
                                if (name.isBlank()) { nameError = "El título es obligatorio"; hasError = true }
                                if (selectedType.isBlank()) { typeError = "Debe seleccionar un tipo"; hasError = true }
                                if (selectedCourseId == null) { courseError = "Debe seleccionar un curso"; hasError = true }
                                if (date.isBlank()) { dateError = "Fecha requerida"; hasError = true }
                                if (time.isBlank()) { timeError = "Hora requerida"; hasError = true }
                                if (weight.isBlank()) { weightError = "Porcentaje requerido"; hasError = true }
                                if (hasError) return@Button

                                val parsedDate = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).parse("$date $time") ?: return@Button
                                val isoDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).format(parsedDate)

                                val evaluationDto = EvaluationDto(
                                    courseId = selectedCourseId ?: return@Button,
                                    name = name,
                                    weight = weight.toDoubleOrNull() ?: return@Button,
                                    date = isoDate,
                                    description = selectedType,
                                    userId = userId?.toIntOrNull() ?: return@Button
                                )

                                if (evaluationToEdit == null) {
                                    viewModel.createEvaluation(
                                        evaluationDto = evaluationDto,
                                        onSuccess = {
                                            onEvaluationCreated()
                                            onDismiss()
                                        },
                                        onError = { errorMsg -> println("Error creando evaluación: $errorMsg") }
                                    )
                                } else {

                                    viewModel.updateEvaluation(
                                        id = evaluationToEdit.typeId,
                                        evaluationDto = evaluationDto,
                                        onSuccess = {
                                            onEvaluationCreated()
                                            onDismiss()
                                        },
                                        onError = { errorMsg -> println("Error actualizando evaluación: $errorMsg") }
                                    )
                                }
                                onDismiss()
                            },
                            modifier = Modifier.weight(1f).height(48.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2BD4BD))
                        ) {
                            Text(if (evaluationToEdit == null) "Guardar" else "Actualizar", fontWeight = FontWeight.Bold)
                        }
                    }

                    val calendar = Calendar.getInstance()
                    if (showDatePicker) {
                        DatePickerDialog(
                            context,
                            { _, year, month, dayOfMonth ->
                                val selectedCalendar = Calendar.getInstance()
                                selectedCalendar.set(year, month, dayOfMonth)
                                date = formatter.format(selectedCalendar.time)
                                showDatePicker = false
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                        ).show()
                    }

                    if (showTimePicker) {
                        TimePickerDialog(
                            context,
                            { _, hourOfDay, minute ->
                                time = String.format("%02d:%02d", hourOfDay, minute)
                                showTimePicker = false
                            },
                            calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE),
                            true
                        ).show()
                    }
                }
            }
        }
    }
}