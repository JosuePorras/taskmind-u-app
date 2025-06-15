package com.moviles.taskmind.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moviles.taskmind.components.Header
import com.moviles.taskmind.components.toast.CustomToast
import com.moviles.taskmind.components.user.PersonalInfoForm
import com.moviles.taskmind.components.user.ProfileCard
import com.moviles.taskmind.components.user.SettingItem
import com.moviles.taskmind.components.user.SettingsCard
import com.moviles.taskmind.models.UserResponse
import com.moviles.taskmind.viewmodel.UserSessionViewModel
import com.moviles.taskmind.viewmodel.toast.ToastViewModel
import com.moviles.taskmind.viewmodel.user.UserViewModel


data class FormErrors(
    val nombre: String? = null,
    val apellido: String? = null,
    val correo: String? = null,
    val carrera: String? = null
)



@Composable
fun UserPage(modifier: Modifier = Modifier,
             userSessionViewModel: UserSessionViewModel,
             userViewModel:UserViewModel= viewModel(),
             tviewModel: ToastViewModel = viewModel()
) {
    val uiState by userViewModel.uiState.collectAsState()
    val toastState by tviewModel.toastState.collectAsState()

    var formErrors by remember { mutableStateOf(FormErrors()) }
    var isEditing by remember { mutableStateOf(false) }
    var nombre by remember { mutableStateOf(userSessionViewModel.userName.value ?: "") }
    var apellido by remember { mutableStateOf(userSessionViewModel.userSecName.value ?: "") }
    var correo by remember { mutableStateOf(userSessionViewModel.userEmail.value ?: "") }
    var carrera by remember { mutableStateOf(userSessionViewModel.userCarrer.value ?: "") }

    fun validateForm(): FormErrors {
        return FormErrors(
            nombre = if (nombre.isBlank()) "El nombre no puede estar vacío" else null,
            apellido = if (apellido.isBlank()) "El apellido no puede estar vacío" else null,
            correo = when {
                correo.isBlank() -> "El correo no puede estar vacío"
                !isValidEmail(correo) -> "Correo electrónico inválido"
                else -> null
            },
            carrera = if (carrera.isBlank()) "La carrera no puede estar vacía" else null
        )
    }

    // Toast message
    LaunchedEffect(uiState.error) {
        uiState.error?.let { errorMessage ->
            tviewModel.showToast(errorMessage, ToastViewModel.ToastType.ERROR)
            userViewModel.clearError()
        }
    }

    LaunchedEffect(uiState.userResponse) {
        uiState.userResponse?.let { user ->
            user.message.let {
                tviewModel.showToast(it, ToastViewModel.ToastType.SUCCESS)
            }
            userViewModel.clearUserResponse()
        }
    }


    LaunchedEffect(toastState.show) {
        if (toastState.show) {
            kotlinx.coroutines.delay(ToastViewModel.ToastDuration.SHORT.timeMillis)
            tviewModel.dismissToast()
        }
    }

    Scaffold(
        topBar = {
            Header(
                title = "Mi Perfil",
                subtitle = "Gestiona tu informacion personal"
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var notificationsEnabled by remember { mutableStateOf(false) }
            var darkModeEnabled by remember { mutableStateOf(false) }

            Spacer(modifier = Modifier.height(16.dp))

            if (isEditing) {
                PersonalInfoForm(
                    nombre = nombre,
                    apellido = apellido,
                    correo = correo,
                    carrera = carrera,
                    onNombreChange = { nombre = it
                        userSessionViewModel.setUserName(it)
                                     },
                    onApellidoChange = { apellido = it
                        userSessionViewModel.setSecName(it)
                    },
                    onCorreoChange = { correo = it
                        userSessionViewModel.setEmail(it)
                    },
                    onCarreraChange = { carrera = it
                        userSessionViewModel.setCareer(it)
                    },
                    onGuardarClick = {
                        val errors = validateForm()
                        formErrors = errors

                        val errorMessages = listOfNotNull(
                            errors.nombre?.let { "Nombre" },
                            errors.apellido?.let { "Apellido" },
                            errors.correo?.let { "Correo electrónico" },
                            errors.carrera?.let { "Carrera" }
                        )

                        if (errorMessages.isNotEmpty()) {
                            val message = "Datos incorrectos, corrige: ${errorMessages.joinToString(", ")}"
                            tviewModel.showToast(message, ToastViewModel.ToastType.ERROR)
                            return@PersonalInfoForm
                        }

                        val UpdateUser= UserResponse(id =0 , ident = userSessionViewModel.userId.value!!,
                            name = nombre,
                            email = correo,
                            carrerDsc = carrera,
                            lastName = apellido,
                            date = "",
                            message = "",
                            password = "",
                            confirmPassword = ""
                        )
                       userViewModel.updateUser(UpdateUser)

                        isEditing = false
                    }
                )
            } else {
                ProfileCard(
                    name = "$nombre $apellido",
                    initials = "${nombre.firstOrNull() ?: ""}${apellido.firstOrNull() ?: ""}".uppercase(),
                    profession = carrera,
                    onEditClick = {
                        isEditing = true
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            SettingsCard(
                title = "Información Personal",
                items = listOf(
                    SettingItem(
                        title = "Carrera",
                        subtitle = carrera,
                        icon = Icons.Default.Info,
                        iconTint = Color(0XFF2BD4BD),
                        iconBackground = Color(0xFFDCFCE7),
                        hasToggle = false
                    ),
                    SettingItem(
                        title = "Correo Electrónico",
                        subtitle = correo,
                        icon = Icons.Default.MailOutline,
                        iconTint = Color(0XFF2BD4BD),
                        iconBackground = Color(0xFFDCFCE7),
                        hasToggle = false
                    )
                ),
                modifier = Modifier.padding(bottom = 24.dp)
            )

            SettingsCard(
                title = "Preferencias",
                items = listOf(
                    SettingItem(
                        title = "Notificaciones",
                        subtitle = "Recibir alertas de eventos",
                        icon = Icons.Default.Notifications,
                        iconTint = Color(0XFF2BD4BD),
                        iconBackground = Color(0xFFDCFCE7),
                        isChecked = notificationsEnabled,
                        onCheckedChange = { notificationsEnabled = it }
                    ),
                    SettingItem(
                        title = "Modo oscuro",
                        subtitle = "Cambiar apariencia",
                        icon = Icons.Default.Settings,
                        iconTint = Color(0XFF2BD4BD),
                        iconBackground = Color(0xFFDCFCE7),
                        isChecked = darkModeEnabled,
                        onCheckedChange = { darkModeEnabled = it }
                    )
                )
            )
        }
        if (toastState.show) {
            CustomToast(
                message = toastState.message,
                toastType = toastState.type,
                onDismiss = { tviewModel.dismissToast() }
            )
        }
    }
}


