package com.moviles.taskmind.pages

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moviles.taskmind.components.CourseCard
import com.moviles.taskmind.components.course.CourseForm
import com.moviles.taskmind.viewmodel.CourseViewModel
import  com.moviles.taskmind.components.Header
import com.moviles.taskmind.components.common.ConfirmationDialog
import com.moviles.taskmind.components.toast.CustomToast
import com.moviles.taskmind.utils.ProgressCalculator
import com.moviles.taskmind.viewmodel.UserSessionViewModel
import com.moviles.taskmind.viewmodel.pdf.PdfUploadViewModel
import com.moviles.taskmind.viewmodel.toast.ToastViewModel
import kotlinx.coroutines.delay

@Composable
fun CoursePage(
    modifier: Modifier = Modifier,
    userSessionViewModel: UserSessionViewModel,
    pdfUploadViewModel: PdfUploadViewModel= viewModel(),
    toastViewModel: ToastViewModel= viewModel(),
) {
    val toastState by toastViewModel.toastState.collectAsState()
    val courseViewModel: CourseViewModel = viewModel()
    val uiState by courseViewModel.uiState.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var showDeleteConfirmation by remember { mutableStateOf(false) }
    var courseToDeleteId by remember { mutableStateOf<String?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scrollState = rememberScrollState()
    val userId = userSessionViewModel.userId.value

    LaunchedEffect(userId) {
        if (!userId.isNullOrBlank()) {
            courseViewModel.fetchCourses(userId)
        }
    }
    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            snackbarHostState.showSnackbar(it)
            courseViewModel.clearError()
        }
    }
    val currentToast = rememberUpdatedState(toastState)
    LaunchedEffect(toastState.message) {
        if (currentToast.value.show) {
            delay(ToastViewModel.ToastDuration.SHORT.timeMillis)
            toastViewModel.dismissToast()
        }
    }

    LaunchedEffect(uiState.isSuccessful) {
        if (uiState.isSuccessful) {
            uiState.message?.let { toastViewModel.showToast(it, ToastViewModel.ToastType.SUCCESS) }
            courseViewModel.clearSuccessCourse()
        }
    }

    fun requestDeleteCourse(courseId: String) {
        courseToDeleteId = courseId
        showDeleteConfirmation = true
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color.White,
        topBar = {
            Header(
                title = "Cursos",
                buttonTitle = "Agregar",
                action = {
                    courseViewModel.clearSelectedCourse()
                    showDialog = true
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(5.dp)
            ) {
                if (uiState.courses.isEmpty()) {
                    Text(
                        text = "No hay cursos disponibles",
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.Gray,
                        fontSize = 18.sp
                    )
                } else {
                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        uiState.courses.forEach { course ->
                            CourseCard(
                                title = course.name,
                                professor = course.professor?.let {
                                    "${it.firstName} ${it.lastNameOne} ${it.lastNameTwo}"
                                } ?: "Sin profesor asignado",
                                email = course.professor?.email ?: "Sin correo asignado",
                                phoneNumber = course.professor?.phone ?: "Sin número de teléfono",
                                code = course.code,
                                progressBar = ProgressCalculator(course.evaluation),
                                event = course.nextEvaluation,
                                colorMain = course.color,
                                listEval = course.evaluation,
                                onEdit = {
                                    courseViewModel.selectCourseForEditing(course)
                                    showDialog = true
                                },
                                onDelete = {
                                    requestDeleteCourse(course.id.toString())
                                }
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
                onDismiss = { toastViewModel.dismissToast() }
            )
        }
    }

    if (showDialog) {
        val selectedCourse by courseViewModel.selectedCourse.collectAsState()

        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {},
            dismissButton = {},
            text = {
                CourseForm(
                    viewModel = courseViewModel,
                    userId = userId,
                    onCourseCreated = { showDialog = false },
                    onDismiss = { showDialog = false },
                    courseToEdit = selectedCourse,
                    pdfModel = pdfUploadViewModel,
                    user = userSessionViewModel
                )
            }
        )
    }

    if (showDeleteConfirmation) {
        ConfirmationDialog(
            title = "Confirmar eliminación",
            message = "¿Estás seguro de que deseas eliminar este curso? Esta acción no se puede deshacer.",
            confirmText = "Eliminar",
            cancelText = "Cancelar",
            confirmButtonColor = Color.Red,
            onConfirm = {
                courseToDeleteId?.let { courseId ->
                    courseViewModel.deleteCourse(courseId, userId ?: "")
                }
                showDeleteConfirmation = false
                courseToDeleteId = null
            },
            onDismiss = {
                showDeleteConfirmation = false
                courseToDeleteId = null
            }
        )
    }
}
