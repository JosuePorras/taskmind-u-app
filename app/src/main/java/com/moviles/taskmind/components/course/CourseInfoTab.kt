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
    nameError: String? = null,
    numberError: String? = null,
    name: String,
    onNameChange: (String) -> Unit,
    number: String,
    onNumberChange: (String) -> Unit,
    selectedColor: Color,
    onColorSelected: (Color) -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        if (nameError != null) {
            Text(text = nameError, color = Color.Red, style = MaterialTheme.typography.bodySmall)
        } else {
            Text(
                "Nombre del Curso*",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF000000),
                modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
            )
        }

        RoundedBlueOutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            labelText = "Ej: Programación IV",
        )

        Spacer(modifier = Modifier.height(16.dp))
        if (numberError != null) {
            Text(text = numberError, color = Color.Red, style = MaterialTheme.typography.bodySmall)
        } else {
            Text(
                "Número de Curso*",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF000000),
            )
        }

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
            Color(0xFFC8ABFC), // purple
            Color(0xFFF6EBA0), // yellow
            Color(0xFFABECBE), // green
            Color(0xFFA0C6FD), // blue
            Color(0xFFFF7B6F)  // red
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