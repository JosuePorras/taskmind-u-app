package com.moviles.taskmind.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moviles.taskmind.components.Header
import com.moviles.taskmind.components.ProfileData
import com.moviles.taskmind.components.homepage.SemesterProgress
import com.moviles.taskmind.components.homepage.TaskCard
import com.moviles.taskmind.viewmodel.UserSessionViewModel

@Composable
fun HomePage(modifier: Modifier = Modifier, userSessionViewModel: UserSessionViewModel) {
    val profile = ProfileData(
        userSessionViewModel.userName.value!!,
        userSessionViewModel.userSecName.value!!
    )

    Scaffold(
        topBar = {
            Header(
                title = "TaskMind",
                profileData = profile
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFFFFFFFF))
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 40.dp, end = 40.dp, top = 24.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Progreso Semestral",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Box(
                    modifier = Modifier
                        .background(Color(0xFFDBEAFE), RoundedCornerShape(16.dp))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "78%",
                        color = Color(0xFF2BD4BD),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            }

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                SemesterProgress(
                    total = 5,
                    approved = 3,
                    pending = 2,
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 40.dp, end = 40.dp, top = 24.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Próximas Evaluaciones",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Column (
                modifier = Modifier.fillMaxWidth()
            ) {
                TaskCard(
                    title = "Examen Parcial",
                    subtitle = "Diseño y programación de plataformas móviles",
                    date = "Hoy, 8:00 AM",
                    backgroundColor = Color(0xFFFFEBEE),
                    iconColor = Color(0xFFE57373),
                    icon = Icons.Default.Book
                )

                TaskCard(
                    title = "Laboratorio",
                    subtitle = "Diseño y programación de plataformas móviles",
                    date = "Sábado, 15 de marzo de 2025",
                    backgroundColor = Color(0xFFFFF9C4),
                    iconColor = Color(0xFFFFB300),
                    icon = Icons.Default.Book
                )

                TaskCard(
                    title = "I Avance Proyecto",
                    subtitle = "Diseño y programación de plataformas móviles",
                    date = "Próximo lunes, 10:00 AM",
                    backgroundColor = Color(0xFFE3F2FD),
                    iconColor = Color(0xFF64B5F6),
                    icon = Icons.Default.Book
                )
            }

        }
    }
}