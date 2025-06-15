package com.moviles.taskmind.pages

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
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
import com.moviles.taskmind.viewmodel.CalendarViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import com.moviles.taskmind.components.calendar.*
import com.moviles.taskmind.models.DayData

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("DefaultLocale")
@Composable
fun CalendarPage(
    modifier: Modifier = Modifier,
    viewModel: CalendarViewModel = viewModel()
) {
    var calendarDate by remember { mutableStateOf(LocalDate.now()) }
    var selectedDay by remember { mutableStateOf<DayData?>(null) }
    val events = viewModel.events
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

    val eventsByDate = remember(events) {
        events.groupBy { it.date }
    }

    val daysList = remember(calendarDate, eventsByDate) {
        (1..daysInMonth).map { day ->
            val dayString = String.format("%04d-%02d-%02d", calendarDate.year, calendarDate.monthValue, day)
            DayData(day, eventsByDate[dayString] ?: emptyList())
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color.White,
        topBar = { Header(title = "Calendario") }
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