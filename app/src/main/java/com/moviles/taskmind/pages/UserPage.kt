package com.moviles.taskmind.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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

@Composable
fun UserPage(modifier: Modifier = Modifier,
             userSessionViewModel: UserSessionViewModel,
             userViewModel:UserViewModel= viewModel(),
             tviewModel: ToastViewModel = viewModel()
) {
    val uiState by userViewModel.uiState.collectAsState()
    val toastState by tviewModel.toastState.collectAsState()
    var isEditing by remember { mutableStateOf(false) }
    var nombre by remember { mutableStateOf(userSessionViewModel.userName.value ?: "") }
    var apellido by remember { mutableStateOf(userSessionViewModel.userSecName.value ?: "") }
    var correo by remember { mutableStateOf(userSessionViewModel.userEmail.value ?: "") }
    var carrera by remember { mutableStateOf(userSessionViewModel.userCarrer.value ?: "") }

    // Efecto para mostrar Toast de error
    LaunchedEffect(uiState.error) {
        uiState.error?.let { errorMessage ->
            tviewModel.showToast(errorMessage, ToastViewModel.ToastType.ERROR)
            userViewModel.clearError() // Asegúrate de limpiar el error después de mostrarlo
        }
    }

    // Efecto para navegar después de login exitoso
    LaunchedEffect(uiState.userResponse) {
        uiState.userResponse?.let { user ->
            user.message?.firstOrNull()?.let {
                tviewModel.showToast(it, ToastViewModel.ToastType.SUCCESS)
            }
userViewModel.clearUserResponse()
        }
    }

    // Toast que se muestra sobre toda la pantalla
    if (toastState.show) {
        Box(modifier = Modifier.fillMaxSize()) {
            CustomToast(
                message = toastState.message,
                toastType = toastState.type,
                onDismiss = { tviewModel.dismissToast() }
            )
        }
    }

    Scaffold(
        topBar = {
            Header(
                title = "Mi Perfil"
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
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
                        val UpdateUser= UserResponse(id =0 , ident = userSessionViewModel.userId.value!!,
                            name = nombre,
                            email = correo,
                            carrerDsc = carrera,
                            lastName = apellido,
                            date = "",
                            message = null,
                            password = ""
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
    }
}
