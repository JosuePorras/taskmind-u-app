package com.moviles.taskmind.pages

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moviles.taskmind.components.Header
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import com.moviles.taskmind.components.calendar.*
import com.moviles.taskmind.models.CalendarEvent
import com.moviles.taskmind.models.DayData
import com.moviles.taskmind.viewmodel.UserSessionViewModel
import com.moviles.taskmind.viewmodel.evaluation.EvaluationViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@SuppressLint("DefaultLocale")
@Composable
fun CalendarPage(
    modifier: Modifier = Modifier,
    evaluationViewModel: EvaluationViewModel = viewModel(),
    userSessionViewModel: UserSessionViewModel = viewModel()
) {
    val userId = userSessionViewModel.userId.value

    LaunchedEffect(userId) {
        if (!userId.isNullOrBlank()) {
            evaluationViewModel.loadEvaluations(userId.toString())
        }
    }

    val uiState by evaluationViewModel.uiState.collectAsState()
    var calendarDate by remember { mutableStateOf(LocalDate.now()) }
    var selectedDay by remember { mutableStateOf<DayData?>(null) }
    val today = LocalDate.now()
    val snackbarHostState = remember { SnackbarHostState() }
    val scrollState = rememberScrollState()

    val calendar = remember(calendarDate) {
        Calendar.getInstance().apply {
            set(Calendar.YEAR, calendarDate.year)
            set(Calendar.MONTH, calendarDate.monthValue - 1)
            set(Calendar.DAY_OF_MONTH, 1)
        }
    }

    val firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1
    val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    val totalBoxes = firstDayOfWeek + daysInMonth

    val evaluationsByDate = remember(uiState.evaluations) {
        uiState.evaluations.groupBy {
            it.date.substring(0, 10) // "2025-06-20T05:09:00.000Z" -> "2025-06-20"
        }
    }

    val daysList = remember(calendarDate, evaluationsByDate) {
        (1..daysInMonth).map { day ->
            val dayString = String.format("%04d-%02d-%02d", calendarDate.year, calendarDate.monthValue, day)
            val items = evaluationsByDate[dayString] ?: emptyList()
            val events = items.map {
                CalendarEvent(
                    name = it.name,
                    description = it.description,
                    date = it.date,
                    courseId = it.courseId,
                    color = getColorForType(it.description)
                )
            }
            DayData(day, events)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color.White,
        topBar = {
            Header(title = "Calendario Académico", subtitle = "Organiza tus actividades importantes.")
        }
    ) { paddingValues ->
        Box(modifier = modifier.fillMaxSize().padding(paddingValues)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(16.dp)
            ) {
                CalendarHeader(
                    calendarDate = calendarDate,
                    onPreviousMonth = { calendarDate = calendarDate.minusMonths(1) },
                    onNextMonth = { calendarDate = calendarDate.plusMonths(1) },
                    calendar = calendar
                )

                Spacer(modifier = Modifier.height(8.dp))
                CalendarWeekHeaders()
                Spacer(modifier = Modifier.height(8.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(7),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentPadding = PaddingValues(top = 8.dp)
                ) {
                    items(totalBoxes) { index ->
                        if (index < firstDayOfWeek) {
                            Box(modifier = Modifier.aspectRatio(1f).padding(4.dp))
                        } else {
                            val dayIndex = index - firstDayOfWeek
                            val dayData = daysList[dayIndex]
                            val currentDate = LocalDate.of(calendarDate.year, calendarDate.monthValue, dayData.day)
                            val isToday = currentDate == today

                            CalendarDayBox(dayData, isToday) {
                                selectedDay = dayData
                            }
                        }
                    }
                }

                EventTypeLabels()
            }

            selectedDay?.let { day ->
                EventCardModal(day.day, day.events) {
                    selectedDay = null
                }
            }
        }
    }
}

fun getColorForType(description: String): Color {
    return when (description.trim()) {
        "Tarea" -> Color(0xFFABECBE)
        "Examen" -> Color(0xFFC8ABFC)
        "Proyecto" -> Color(0xFFA0C6FD)
        "Exposición" -> Color(0xFFFF7B6F)
        "Laboratorio" -> Color(0xFFF6EBA0)
        else -> Color.LightGray
    }
}

@Composable
fun CalendarHeader(calendarDate: LocalDate, onPreviousMonth: () -> Unit, onNextMonth: () -> Unit, calendar: Calendar) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onPreviousMonth) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Mes anterior")
        }
        Text(
            text = SimpleDateFormat("MMMM yyyy", Locale("es")).format(calendar.time),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        IconButton(onClick = onNextMonth) {
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Mes siguiente")
        }
    }
}

@Composable
fun CalendarWeekHeaders() {
    val headers = listOf("Dom", "Lun", "Mar", "Mié", "Jue", "Vie", "Sáb")
    Row(modifier = Modifier.fillMaxWidth()) {
        headers.forEach {
            Text(
                text = it,
                modifier = Modifier.weight(1f),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray,
                textAlign = TextAlign.Center
            )
        }
    }
}