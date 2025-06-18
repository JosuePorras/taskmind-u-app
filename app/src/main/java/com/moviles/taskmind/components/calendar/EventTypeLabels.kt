package com.moviles.taskmind.components.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EventTypeLabels() {
    val types = listOf(
        "Proyecto" to Color(0xFF90CAF9),
        "Laboratorio" to Color(0xFFFFF59D),
        "Tarea" to Color(0xFFA5D6A7),
        "Examen" to Color(0xFFE1BEE7),
        "Presentación" to Color(0xFFFFCDD2)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        FlowRow(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            types.forEach { (label, color) ->
                Text(
                    text = label,
                    modifier = Modifier
                        .background(color, shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                    fontSize = 12.sp
                )
            }
        }
    }
}