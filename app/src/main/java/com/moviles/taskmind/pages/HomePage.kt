package com.moviles.taskmind.pages

import android.util.Log
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.messaging.FirebaseMessaging
import com.moviles.taskmind.components.Header
import com.moviles.taskmind.components.ProfileData
import com.moviles.taskmind.components.homepage.SemesterProgress
import com.moviles.taskmind.components.homepage.TaskCard
import com.moviles.taskmind.services.MessagingService
import com.moviles.taskmind.utils.darkenColorHex
import com.moviles.taskmind.utils.getEvaluationIcon
import com.moviles.taskmind.utils.parseColorString
import com.moviles.taskmind.viewmodel.UserSessionViewModel
import com.moviles.taskmind.viewmodel.homepage.HomePageViewModel
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    userSessionViewModel: UserSessionViewModel,
    homePageViewModel: HomePageViewModel = viewModel()
) {
    val uiState by homePageViewModel.uiState.collectAsState()

    val profile = ProfileData(
        userSessionViewModel.userName.value ?: "",
        userSessionViewModel.userSecName.value ?: ""
    )

    LaunchedEffect(Unit) {
        val userId = userSessionViewModel.userId.value
        if (!userId.isNullOrEmpty()) {
            homePageViewModel.fetchHomeStatus(userId)
        }
    }

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        val userId = userSessionViewModel.userId.value
        if (!userId.isNullOrEmpty()) {
            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val token = task.result
                    MessagingService.sendFcmTokenToBackend(context = context, userId = userId, token = token)
                } else {
                    Log.e("FCM", "No se pudo obtener token: ${task.exception}")
                }
            }
        }
    }

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
                .background(Color.White)
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            if (uiState.isLoading) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFF2BD4BD))
                }
            } else if (uiState.error != null) {
                Text(
                    text = "Error: ${uiState.error}",
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                uiState.courseStatus?.let { resumen ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp, top = 24.dp, bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Progreso Semestral",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        val progreso = if (resumen.courseTotal != 0)
                            ((resumen.approve.toFloat() / resumen.courseTotal) * 100).toInt()
                        else 0
                        Box(
                            modifier = Modifier
                                .background(Color(0xFFDBEAFE), RoundedCornerShape(16.dp))
                                .padding(horizontal = 12.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "$progreso%",
                                color = Color(0xFF2BD4BD),
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }
                    }

                    Box(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 10.dp),

                    ) {
                        SemesterProgress(
                            total = resumen.courseTotal,
                            approved = resumen.approve,
                            pending = resumen.pending
                        )
                    }
                }

                Text(
                    text = "Próximas Evaluaciones",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 40.dp, end = 40.dp, top = 24.dp, bottom = 8.dp)
                )

                Column(modifier = Modifier.fillMaxWidth()) {
                    uiState.evualuationProx.forEach { evaluations ->
                        val resolvedColor = darkenColorHex(evaluations.color)
                        val backColor = parseColorString(evaluations.color)
                        TaskCard(
                            title = evaluations.name,
                            subtitle = evaluations.courseName,
                            date = dateFormat(evaluations.date),
                            backgroundColor = backColor,
                            iconColor = resolvedColor,
                            icon = getEvaluationIcon(evaluations.details)
                        )
                    }
                }
            }
        }
    }
}


fun dateFormat(fechaISO: String): String {
    return try {
        val utcDateTime = ZonedDateTime.parse(fechaISO, DateTimeFormatter.ISO_DATE_TIME)

        val costaRicaZone = ZoneId.of("America/Costa_Rica")
        val fechaCR = utcDateTime.withZoneSameInstant(costaRicaZone).toLocalDateTime()

        val diaSemana = fechaCR.dayOfWeek.getDisplayName(java.time.format.TextStyle.FULL, Locale("es"))
        val hora = fechaCR.format(DateTimeFormatter.ofPattern("h:mm a", Locale("es")))

        "${diaSemana.replaceFirstChar { it.uppercase() }} a las $hora"
    } catch (e: Exception) {
        "Fecha inválida"
    }
}