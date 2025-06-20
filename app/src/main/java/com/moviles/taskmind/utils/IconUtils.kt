package com.moviles.taskmind.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Class
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PresentToAll
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Science
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun getEvaluationIcon(type: String): ImageVector {
    return when (type) {
        "Tarea" -> Icons.Default.Edit
        "Examen" -> Icons.Default.Class
        "Proyecto" -> Icons.Default.Build
        "Presentación" -> Icons.Default.PresentToAll
        "Laboratorio" -> Icons.Default.Science
        else -> Icons.Default.School
    }
}
