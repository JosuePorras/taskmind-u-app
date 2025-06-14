package com.moviles.taskmind.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.moviles.taskmind.components.Header
import com.moviles.taskmind.components.user.PersonalInfoForm
import com.moviles.taskmind.components.user.ProfileCard
import com.moviles.taskmind.components.user.SettingItem
import com.moviles.taskmind.components.user.SettingsCard

@Composable
fun UserPage(modifier: Modifier = Modifier) {
    var isEditing by remember { mutableStateOf(false) }
    var nombre by remember { mutableStateOf("Aaron") }
    var apellido by remember { mutableStateOf("Matarrita") }
    var correo by remember { mutableStateOf("aaron.matarrita.portuguez@est.una.ac.cr") }
    var carrera by remember { mutableStateOf("Ingeniería en Sistemas") }
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
                    onNombreChange = { nombre = it },
                    onApellidoChange = { apellido = it },
                    onCorreoChange = { correo = it },
                    onCarreraChange = { carrera = it },
                    onGuardarClick = {
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
