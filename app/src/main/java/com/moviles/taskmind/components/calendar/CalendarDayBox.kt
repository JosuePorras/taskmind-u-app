package com.moviles.taskmind.components.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moviles.taskmind.models.DayData

@Composable
fun CalendarDayBox(dayData: DayData, isToday: Boolean, onClick: () -> Unit) {
    val labelColor = mapOf(
        "Proyecto" to Color(0xFF90CAF9),
        "Laboratorio" to Color(0xFFFFF59D),
        "Tarea" to Color(0xFFA5D6A7),
        "Examen" to Color(0xFFE1BEE7),
        "Presentación" to Color(0xFFFFCDD2)
    )

    val backgroundColor = if (isToday) Color(0xFFB3E5FC) else Color(0xFFF8F8F8)

    Column(
        modifier = Modifier
            .padding(2.dp)
            .aspectRatio(0.9f)
            .background(backgroundColor, shape = MaterialTheme.shapes.small)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = dayData.day.toString(),
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 4.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp),
            modifier = Modifier
                .padding(horizontal = 2.dp)
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
        ) {
            val maxLabels = 5
            dayData.events.take(maxLabels).forEach {
                val bgColor = labelColor[it.type] ?: Color.LightGray
                Text(
                    text = it.type,
                    fontSize = 9.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(bgColor)
                        .padding(horizontal = 4.dp, vertical = 2.dp),
                    color = Color.Black
                )
            }
            if (dayData.events.size > maxLabels) {
                Text(
                    text = "+${dayData.events.size - maxLabels} más",
                    fontSize = 9.sp,
                    color = Color.DarkGray
                )
            }
        }
    }
}