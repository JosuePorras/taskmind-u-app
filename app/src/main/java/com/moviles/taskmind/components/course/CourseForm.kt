package com.moviles.taskmind.components.course

import android.net.Uri
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moviles.taskmind.components.toast.CustomToast
import com.moviles.taskmind.models.Course
import com.moviles.taskmind.models.CourseDto
import com.moviles.taskmind.models.Professor
import com.moviles.taskmind.utils.toHexString
import com.moviles.taskmind.viewmodel.CourseViewModel
import com.moviles.taskmind.viewmodel.UserSessionViewModel
import com.moviles.taskmind.viewmodel.pdf.PdfUploadViewModel
import com.moviles.taskmind.viewmodel.toast.ToastViewModel
import kotlinx.coroutines.delay

@Composable
fun CourseForm(
    viewModel: CourseViewModel,
    userId: String?,
    onCourseCreated: () -> Unit,
    onDismiss: () -> Unit,
    courseToEdit: CourseDto? = null,
    pdfModel: PdfUploadViewModel,
    toastNot:ToastViewModel= viewModel(),
    user: UserSessionViewModel
) {
    val toastState by toastNot.toastState.collectAsState()
    var name by remember { mutableStateOf("") }
    var number by remember { mutableStateOf("") }
    var schedule by remember { mutableStateOf("") }
    var selectedColor by remember { mutableStateOf(Color(0xFF2196F3)) }
    val state = pdfModel.uiState
    var professorName by remember { mutableStateOf("") }
    var professorFirstName by remember { mutableStateOf("") }
    var professorLastName by remember { mutableStateOf("") }
    var professorEmail by remember { mutableStateOf("") }
    var professorPhone by remember { mutableStateOf("") }

    var useExistingProfessor by remember { mutableStateOf<Boolean?>(null) }
    var selectedProfessorId by remember { mutableStateOf<Int?>(null) }

    val professors by viewModel.professors.collectAsState()

    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Curso", "Profesor","Importar")

    val scrollState = rememberScrollState()

    val userIdInt = userId?.toIntOrNull() ?: 0

    var nameError by remember { mutableStateOf<String?>(null) }
    var numberError by remember { mutableStateOf<String?>(null) }
    var professorNameError by remember { mutableStateOf<String?>(null) }
    var professorFirstNameError by remember { mutableStateOf<String?>(null) }
    var professorLastNameError by remember { mutableStateOf<String?>(null) }
    var professorEmailError by remember { mutableStateOf<String?>(null) }
    var professorPhoneError by remember { mutableStateOf<String?>(null) }
    var selectedPdfUri by remember { mutableStateOf<Uri?>(null) }
    var selectedPdfBytes by remember { mutableStateOf<ByteArray?>(null) }
    val context=LocalContext.current

    LaunchedEffect(userId, courseToEdit) {
        userId?.let {
            viewModel.fetchProfessorsByUser(it)
        }

        if (courseToEdit != null) {
            name = courseToEdit.name
            number = courseToEdit.code
            schedule = courseToEdit.schedule
            selectedColor = Color(android.graphics.Color.parseColor(courseToEdit.color))
            selectedProfessorId = courseToEdit.professorId
            useExistingProfessor = true
        } else {
            name = ""
            number = ""
            schedule = ""
            selectedColor = Color(0xFF2196F3)
            selectedProfessorId = null
            useExistingProfessor = null
        }

        professorName = ""
        professorFirstName = ""
        professorLastName = ""
        professorEmail = ""
        professorPhone = ""
    }
    val currentToast = rememberUpdatedState(toastState)

    LaunchedEffect(toastState.message) {
        if (currentToast.value.show) {
            delay(ToastViewModel.ToastDuration.SHORT.timeMillis)
            toastNot.dismissToast()
        }
    }


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

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (courseToEdit != null) "Editar curso" else "Crear nuevo curso",
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


                    CourseTabSelector(tabs, selectedTabIndex) { selectedTabIndex = it }

                    Spacer(modifier = Modifier.height(16.dp))

                    if (selectedTabIndex == 0) {
                        CourseInfoTab(
                            name = name,
                            onNameChange = { name = it },
                            number = number,
                            onNumberChange = { number = it },
                            selectedColor = selectedColor,
                            onColorSelected = { selectedColor = it },
                            nameError = nameError,
                            numberError = numberError,
                        )
                    } else if(selectedTabIndex == 1){
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
                            onProfessorPhoneChange = { professorPhone = it },
                            isEditMode = courseToEdit != null
                        )
                    }else {
                        Column(modifier = Modifier.padding(16.dp)) {
                            FilePicker(
                                onPdfSelected = { uri ->
                                    selectedPdfUri = uri
                                    selectedPdfBytes = readPdfAsByteArray(context, uri)
                                }
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            selectedPdfUri?.let {
                                Text("Archivo seleccionado: ${it.lastPathSegment}", fontWeight = FontWeight.Bold)
                            }

                            selectedPdfBytes?.let {
                                Text("Tamaño del PDF: ${it.size} bytes")
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = {
                                    pdfModel.uploadPdf(user.userId.value!!, selectedPdfBytes!!, viewModel)
                                },
                                enabled = selectedPdfBytes != null && !state.isLoading,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF4285F4) // Azul estilo Google Drive
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp)
                                    .shadow(4.dp, RoundedCornerShape(12.dp))
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CloudUpload,
                                    contentDescription = "Subir PDF",
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Subir PDF",
                                    color = Color.White,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            when {
                                state.isLoading -> {
                                    CircularProgressIndicator()
                                }

                                state.isSuccess -> {
                                    toastNot.showToast("¡PDF subido exitosamente!", ToastViewModel.ToastType.SUCCESS)
                                    state.isSuccess=false;
                                }

                                state.message != null -> {
                                    toastNot.showToast("Error al subir el pdf", ToastViewModel.ToastType.ERROR)
                                }
                            }
                        }
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
                                    professorId = if (currentUseExistingProfessor) {
                                        currentSelectedProfessorId ?: courseToEdit?.professorId
                                    } else {
                                        null
                                    },
                                    userId = userIdInt
                                )

                                // Reset errors
                                nameError = null
                                numberError = null
                                professorEmailError = null
                                professorPhoneError = null

                                var hasError = false

                                if (name.isBlank()) {
                                    nameError = "El nombre es obligatorio"
                                    hasError = true
                                }

                                if (number.isBlank()) {
                                    numberError = "El código es obligatorio"
                                    hasError = true
                                }

                                if (useExistingProfessor == false) {
                                    if (professorName.isBlank()) {
                                        professorNameError = "Nombre requerido"
                                        hasError = true
                                    }

                                    if (professorFirstName.isBlank()) {
                                        professorFirstNameError = "Primer apellido requerido"
                                        hasError = true
                                    }

                                    if (professorLastName.isBlank()) {
                                        professorLastNameError = "Segundo apellido requerido"
                                        hasError = true
                                    }

                                    if (professorEmail.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(professorEmail).matches()) {
                                        professorEmailError = "Correo inválido"
                                        hasError = true
                                    }

                                    if (professorPhone.isBlank() || !professorPhone.all { it.isDigit() }) {
                                        professorPhoneError = "Teléfono inválido"
                                        hasError = true
                                    }
                                }

                                if (hasError) return@Button

                                if (courseToEdit == null) {
                                    viewModel.addCourse(course, professor, currentSelectedProfessorId)
                                } else {
                                    viewModel.updateCourse(course.copy(id = courseToEdit.id, professorId = course.professorId ?: courseToEdit.professorId))
                                }

                                onCourseCreated()
                                viewModel.clearSelectedCourse()
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
                                "Guardar",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
        if (toastState.show) {
            CustomToast(
                message = toastState.message,
                toastType = toastState.type,
                onDismiss = { toastNot.dismissToast() }
            )
        }
    }
}