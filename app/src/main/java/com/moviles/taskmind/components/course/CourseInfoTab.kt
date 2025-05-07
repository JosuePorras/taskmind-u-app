package com.moviles.taskmind.components.course

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.moviles.taskmind.components.RoundedBlueOutlinedTextField

@Composable
fun CourseInfoTab(
    name: String,
    onNameChange: (String) -> Unit,
    number: String,
    onNumberChange: (String) -> Unit,
    selectedColor: Color,
    onColorSelected: (Color) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            "Nombre del Curso*",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF000000),
            modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
        )

        RoundedBlueOutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            labelText = "Ej: Programación IV",
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "Número de Curso*",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF000000),
        )

        RoundedBlueOutlinedTextField(
            value = number,
            onValueChange = onNumberChange,
            labelText = "Ej: FIF-404",
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            "Color del curso",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF000000),
            modifier = Modifier.padding(bottom = 12.dp, start = 4.dp)
        )

        val colors = listOf(
            Color(0xFF9C27B0), // Morado
            Color(0xFFFFEB3B), // Amarillo
            Color(0xFF4CAF50), // Verde
            Color(0xFF2196F3), // Azul
            Color(0xFFFF5252)  // Rojo
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            colors.forEach { color ->
                ColorOption(color = color, isSelected = selectedColor == color) {
                    onColorSelected(color)
                }
            }
        }
    }
}